package kirpiks.erp.meta;

import kirpiks.erp.db.DBManager;

import java.sql.SQLException;
import java.util.*;

public abstract class CatalogManager {

    abstract public String getTableName();

    public Map<String, String> getColTypes() {
        Map<String, String> map = new HashMap<>(CatalogObject.getColTypes());
        return map;
    }

    public void createTable() throws SQLException {

        DBManager.getInstance().createTable(getTableName(), getColTypes());
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
            DBManager.getInstance().update(
                    getTableName(), el.getParams(), CatalogObject.COL_REF);
        }
    }

    public <T extends CatalogObject> void read(T el) throws SQLException {

        Map<String, Object> res = DBManager.getInstance()
                .select(getTableName(), el.getParams(), CatalogObject.COL_REF);

        el.setFromParams(res);
    }

}
