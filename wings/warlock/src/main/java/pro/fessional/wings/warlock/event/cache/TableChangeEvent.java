package pro.fessional.wings.warlock.event.cache;

import lombok.Data;
import pro.fessional.wings.warlock.event.WarlockMetadataEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author trydofor
 * @since 2021-03-07
 */
@Data
public class TableChangeEvent implements WarlockMetadataEvent {

    public static final int INSERT = 1 << 1;
    public static final int UPDATE = 1 << 2;
    public static final int DELETE = 1 << 3;

    private List<String> source = new LinkedList<>();
    private int change = 0;
    private String table;
    private Map<String, List<?>> field = Collections.emptyMap();

    public boolean isInsert() {
        return (change & INSERT) != 0;
    }

    public boolean isUpdate() {
        return (change & UPDATE) != 0;
    }

    public boolean isDelete() {
        return (change & DELETE) != 0;
    }

    public boolean hasChange(int mod) {
        return mod == 0 || (change & mod) != 0;
    }

    public void addSource(String src) {
        source.add(src);
    }

    public boolean hasSource(String src) {
        return source.contains(src);
    }
}
