package kirpiks.erp.meta;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class CatalogObject {

    public static final String COL_REF = "Ref";
    public static final String COL_DELETION_MARK = "DeletionMark";
    public static final String COL_CODE = "Code";
    public static final String COL_DESCRIPTION = "Description";

    public static Map<String, String> getColTypes() {

        Map<String, String> map = new HashMap<>();
        map.put(COL_REF,            "CHAR(36)");
        map.put(COL_DELETION_MARK,  "BOOLEAN");
        map.put(COL_CODE,           "INT");
        map.put(COL_DESCRIPTION,    "VARCHAR(100)");

        return map;
    }

    boolean inDatabase = false;
    CatalogManager mgr = null;

    private String ref;
    private boolean deletionMark = false;
    private int code;
    private String description;

    public Map<String, Object> getParams() {

        Map<String, Object> params = new HashMap<>();
        params.put(COL_REF              , getRef());
        params.put(COL_DELETION_MARK    , isDeletionMark());
        params.put(COL_CODE             , getCode());
        params.put(COL_DESCRIPTION      , getDescription());

        return params;
    }

    public void setFromParams(Map<String, Object> map) {

        setRef((String)             map.get(COL_REF));
        setDeletionMark((boolean)   map.get(COL_DELETION_MARK));
        setCode((int)               map.get(COL_CODE));
        setDescription((String)     map.get(COL_DESCRIPTION));

    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public boolean isDeletionMark() {
        return deletionMark;
    }

    public void setDeletionMark(boolean deletionMark) {
        this.deletionMark = deletionMark;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void save() throws SQLException {
        mgr.save(this);
    }

    public void read() throws SQLException {
        mgr.read(this);
    }

}
