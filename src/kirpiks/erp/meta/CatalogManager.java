package kirpiks.erp.meta;

import kirpiks.erp.db.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class CatalogManager {

    private static final String[] COLUMN_NAMES = {
            "Ref CHAR(36)",
            "DeletionMark BOOLEAN",
            "Code INT",
            "Description VARCHAR(100)"
    };

    abstract public String getTableName();

    public List<String> getTableColumns() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, COLUMN_NAMES);
        return list;
    }

    public void createTable() throws SQLException {

        DBManager.getInstance().createTable(getTableName(), getTableColumns());
    }

    public void dropTable() throws SQLException {

        DBManager.getInstance().dropTable(getTableName());
    }

    protected abstract <T> T createConcreteElement();

    public <T extends CatalogObject> T createElement() {

        T el = createConcreteElement();

        el.inDatabase = false;
        el.mgr = this;
        el.setRef(UUID.randomUUID().toString());

        return el;
    }

    public void save(CatalogObject el) throws SQLException {
        if (!el.inDatabase) {
            DBManager.getInstance().insert(getTableName(), el.getParams());
            el.inDatabase = true;
        } else {
            DBManager.getInstance().update(getTableName(), el.getParams(), "Ref");
        }
    }

}
