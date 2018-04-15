package kirpiks.erp.meta;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class CatalogObject {

    boolean inDatabase = false;
    CatalogManager mgr = null;

    private String ref;
    private boolean deletionMark = false;
    private int code;
    private String description;

    public Map<String, Object> getParams() {

        Map<String, Object> params = new HashMap<>();
        params.put("Ref", getRef());
        params.put("DeletionMark", isDeletionMark());
        params.put("Code", getCode());
        params.put("Description", getDescription());

        return params;
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
}
