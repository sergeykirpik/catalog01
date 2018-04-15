package kirpiks.erp.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataSet extends ArrayList< Map<String, Object> > {

    private Map<String, Object> currentRow = null;

    public void newRow() {
        currentRow = new HashMap<>();
        this.add(currentRow);
    }

    public void put(String key, Object value) {
        currentRow.put(key, value);
    }
}
