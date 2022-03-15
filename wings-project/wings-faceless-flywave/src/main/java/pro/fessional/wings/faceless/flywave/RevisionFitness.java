package pro.fessional.wings.faceless.flywave;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pro.fessional.mirana.math.AnyIntegerUtil;
import pro.fessional.wings.faceless.flywave.SchemaRevisionManager.RevisionSql;
import pro.fessional.wings.faceless.flywave.SchemaRevisionManager.Status;
import pro.fessional.wings.faceless.util.FlywaveRevisionScanner;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author trydofor
 * @since 2022-03-15
 */
@Slf4j
public class RevisionFitness {

    private final Set<String> scanPath = new HashSet<>();
    private final TreeMap<Long, Set<Act>> reviAct = new TreeMap<>(); // No Skip
    private final HashMap<Long, Set<String>> reviMsg = new HashMap<>();

    public void addFit(Fit fit, String msg) {
        if (fit == null) return;
        final Act act = fit.getLost();
        if (act == null || act == Act.SKIP) {
            log.info("skip fit {}", msg);
            return;
        }

        final Set<String> path = fit.path;
        if (path != null) {
            this.scanPath.addAll(path);
        }

        final Set<String> revi = fit.getRevi();
        for (String str : revi) {
            Long r = AnyIntegerUtil.obj64(str);
            reviAct.computeIfAbsent(r, k -> new HashSet<>()).add(act);
            reviMsg.computeIfAbsent(r, k -> new HashSet<>()).add(msg);
        }

        log.info("found fit {}. `wings.faceless.flywave.fit[{}].lost=SKIP` to skip", revi, msg);
    }

    public void addFits(Map<String, Fit> fits) {
        for (Map.Entry<String, Fit> ent : fits.entrySet()) {
            addFit(ent.getValue(), ent.getKey());
        }
    }

    public void checkRevision(SchemaRevisionManager revisionManager) {
        revisionManager.askWay(it -> true);
        revisionManager.logWay((s, s2) -> {});
        final TreeMap<Long, Set<Act>> revi = checkUnapply(revisionManager);
        applyRevision(revisionManager, revi);
    }

    private void applyRevision(SchemaRevisionManager manager, TreeMap<Long, Set<Act>> revi) {
        TreeMap<Long, Set<String>> exec = new TreeMap<>();
        boolean failed = false;
        for (Map.Entry<Long, Set<Act>> en : revi.entrySet()) {
            final Long rv = en.getKey();
            final Set<Act> ts = en.getValue();
            final Set<String> ms = reviMsg.get(rv);
            if (ts.contains(Act.WARN)) {
                if (log.isWarnEnabled()) {
                    log.warn("Wings Revision Lost revi={}. Manual={}", rv, manual(ms));
                }
            }
            if (ts.contains(Act.FAIL)) {
                if (log.isErrorEnabled()) {
                    log.error("Wings Revision Lost revi={}. Manual={}", rv, manual(ms));
                }
                failed = true;
            }
            if (ts.contains(Act.EXEC)) {
                exec.put(rv, ms);
            }
        }

        if (failed) {
            throw new IllegalStateException("Wings Revision Lost revi need FAIL");
        }
        if (exec.isEmpty()) {
            return;
        }

        final SortedMap<Long, RevisionSql> scan = FlywaveRevisionScanner.scan(scanPath);

        // check first
        boolean errors = false;
        for (Map.Entry<Long, Set<String>> en : exec.entrySet()) {
            Long rv = en.getKey();
            final Set<String> ms = en.getValue();
            final RevisionSql sql = scan.get(rv);
            if (sql == null) {
                log.error("Wings Revision Lost And Failed to Scan. revi={} by={}", rv, ms);
                errors = true;
            }
        }

        if (errors) {
            throw new IllegalStateException("Wings Revision Lost And Failed");
        }

        // exec sql
        final long cid = -System.currentTimeMillis();
        for (Map.Entry<Long, Set<String>> en : exec.entrySet()) {
            Long rv = en.getKey();
            final Set<String> ms = en.getValue();
            final RevisionSql sql = scan.get(rv);
            if (rv == WingsRevision.V00_19_0512_01_Schema.revision()) {
                TreeMap<Long, RevisionSql> init = new TreeMap<>();
                init.put(rv, sql);
                log.info("Wings Revision force to init revi={}, cid={}, by={}", rv, cid, ms);
                manager.checkAndInitSql(init, cid, true);
            }
            else {
                manager.forceUpdateSql(sql, cid);
                log.info("Wings Revision force to apply revi={}, cid={}, by={}", rv, cid, ms);
                manager.forceApplyBreak(rv, cid, true, null);
            }
        }
    }

    private String manual(Set<String> ms) {
        StringBuilder sb = new StringBuilder();
        sb.append("replace XXX with SKIP to skip, EXEC to exec sqls: ");
        for (String m : ms) {
            sb.append("\n wings.faceless.flywave.fit[").append(m).append("].lost=XXX");
        }
        return sb.toString();
    }

    private TreeMap<Long, Set<Act>> checkUnapply(SchemaRevisionManager manager) {
        HashMap<Long, Status> reviStatus = null;
        boolean diff = false;
        for (Map.Entry<String, SortedMap<Long, Status>> en : manager.statusRevisions().entrySet()) {
            final String db = en.getKey();
            log.info("Wings Revision Check Database={}", db);
            if (reviStatus == null) {
                reviStatus = new HashMap<>(en.getValue());
            }
            else {
                Map<Long, Status> lm = new HashMap<>(reviStatus);
                Map<Long, Status> rm = new HashMap<>();
                for (Map.Entry<Long, Status> st : en.getValue().entrySet()) {
                    final Long rv = st.getKey();
                    final Status rs = st.getValue();
                    if (rs == lm.get(rv)) {
                        lm.remove(rv);
                    }
                    else {
                        rm.put(rv, rs);
                    }
                }
                if (!lm.isEmpty()) {
                    diff = true;
                    for (Map.Entry<Long, Status> e : lm.entrySet()) {
                        log.warn("Wings Revision Diff lost revi={}, status={}", e.getKey(), e.getValue());
                    }
                }
                if (!rm.isEmpty()) {
                    diff = true;
                    for (Map.Entry<Long, Status> e : rm.entrySet()) {
                        log.warn("Wings Revision Diff have revi={}, status={}", e.getKey(), e.getValue());
                    }
                }
            }
        }

        if (diff) {
            throw new IllegalStateException("Wings Revision Diff Found, check the log warnning");
        }

        if (reviStatus == null || reviStatus.isEmpty()) {
            log.info("Wings Revision Unapply all-revi");
            return reviAct;
        }
        else {
            final TreeMap<Long, Set<Act>> map = new TreeMap<>();
            for (Map.Entry<Long, Set<Act>> revi : reviAct.entrySet()) {
                final Long rv = revi.getKey();
                final Status st = reviStatus.get(rv);
                if (st != Status.Applied) {
                    map.put(rv, revi.getValue());
                    log.info("Wings Revision Unapply revi={}, status={}", rv, st);
                }
            }
            return map;
        }
    }

    // ////////

    public enum Act {
        /**
         * 跳过检查
         */
        SKIP,
        /**
         * 在日志中WARN
         */
        WARN,
        /**
         * 程序异常终止
         */
        FAIL,
        /**
         * 强制执行
         * forceUpdateSql and forceApplyBreak
         */
        EXEC,
    }

    @Data
    public static class Fit {
        private Set<String> path = Collections.emptySet();
        private Set<String> revi = Collections.emptySet();
        private Act lost = Act.WARN;
    }
}
