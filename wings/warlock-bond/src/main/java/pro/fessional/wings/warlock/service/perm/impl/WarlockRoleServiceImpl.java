package pro.fessional.wings.warlock.service.perm.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.pain.CodeException;
import pro.fessional.wings.faceless.service.journal.JournalService;
import pro.fessional.wings.faceless.service.lightid.LightIdService;
import pro.fessional.wings.warlock.caching.CacheEventHelper;
import pro.fessional.wings.warlock.database.autogen.tables.WinRoleEntryTable;
import pro.fessional.wings.warlock.database.autogen.tables.daos.WinRoleEntryDao;
import pro.fessional.wings.warlock.database.autogen.tables.pojos.WinRoleEntry;
import pro.fessional.wings.warlock.enums.errcode.CommonErrorEnum;
import pro.fessional.wings.warlock.event.cache.TableChangeEvent;
import pro.fessional.wings.warlock.service.perm.WarlockPermNormalizer;
import pro.fessional.wings.warlock.service.perm.WarlockRoleService;

import java.util.Collections;
import java.util.Map;

import static pro.fessional.wings.warlock.caching.CacheConst.WarlockRoleService.CacheManager;
import static pro.fessional.wings.warlock.caching.CacheConst.WarlockRoleService.CacheName;
import static pro.fessional.wings.warlock.caching.CacheConst.WarlockRoleService.EventTables;

/**
 * @author trydofor
 * @since 2021-03-07
 */
@Slf4j
@CacheConfig(cacheNames = CacheName, cacheManager = CacheManager)
public class WarlockRoleServiceImpl implements WarlockRoleService {

    @Setter(onMethod_ = {@Autowired})
    protected WinRoleEntryDao winRoleEntryDao;

    @Setter(onMethod_ = {@Autowired})
    protected LightIdService lightIdService;

    @Setter(onMethod_ = {@Autowired})
    protected JournalService journalService;

    @Setter(onMethod_ = {@Autowired})
    protected WarlockPermNormalizer permNormalizer;

    @Override
    @Cacheable
    public Map<Long, String> loadRoleAll() {
        if (winRoleEntryDao.notTableExist()) return Collections.emptyMap();

        final WinRoleEntryTable t = winRoleEntryDao.getTable();

        final Map<Long, String> all = winRoleEntryDao
                .ctx()
                .select(t.Id, t.Name)
                .from(t)
                .where(t.onlyLiveData)
                .fetch()
                .intoMap(Record2::value1, it -> permNormalizer.role(it.value2()));
        log.info("loadRoleAll size={}", all.size());
        return all;
    }

    /**
     * 异步清理缓存，event可以为null
     *
     * @param event 可以为null
     */
    @EventListener
    @CacheEvict(allEntries = true, condition = "#result")
    public boolean evictRoleAllCache(TableChangeEvent event) {
        final String tb = CacheEventHelper.fire(event, EventTables);
        if (tb != null) {
            log.info("evictRoleAllCache by {}, {}", tb, event == null ? -1 : event.getChange());
            return true;
        }

        return false;
    }

    @Override
    public long create(@NotNull String name, String remark) {
        if (!StringUtils.hasText(name)) {
            throw new CodeException(CommonErrorEnum.AssertEmpty1, "role.name");
        }

        return journalService.submit(Jane.Create, name, remark, commit -> {
            final WinRoleEntryTable t = winRoleEntryDao.getTable();
            long id = lightIdService.getId(t);
            WinRoleEntry po = new WinRoleEntry();
            po.setId(id);
            po.setName(name);
            po.setRemark(Null.notNull(remark));
            commit.create(po);

            try {
                winRoleEntryDao.insert(po);
            }
            catch (Exception e) {
                log.error("failed to insert role entry. name=" + name + ", remark=" + remark, e);
                throw new CodeException(e, CommonErrorEnum.AssertState2, "role.name", name);
            }

            return id;
        });
    }

    @Override
    public void modify(long roleId, String remark) {
        journalService.commit(Jane.Modify, roleId, remark, commit -> {
            final WinRoleEntryTable t = winRoleEntryDao.getTable();
            final int rc = winRoleEntryDao
                    .ctx()
                    .update(t)
                    .set(t.CommitId, commit.getCommitId())
                    .set(t.ModifyDt, commit.getCommitDt())
                    .set(t.Remark, remark)
                    .where(t.Id.eq(roleId))
                    .execute();
            log.info("modify perm remark. roleId={}, affect={}", roleId, rc);
        });
    }
}
