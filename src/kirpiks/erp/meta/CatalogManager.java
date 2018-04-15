package kirpiks.erp.meta;

import kirpiks.erp.db.DBManager;
import kirpiks.erp.db.DataSet;

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

        DataSet res = DBManager.getInstance()
                .select(getTableName(), el.getParams(), CatalogObject.COL_REF);

        el.setFromParams(res.get(0));
    }

    public <T extends CatalogObject> T findByCode(int code) throws SQLException {
        return findBy(CatalogObject.COL_CODE, code);
    }

    public <T extends CatalogObject> T findByDescription(String desc) throws SQLException {
        return findBy(CatalogObject.COL_DESCRIPTION, desc);
    }

    public <T extends CatalogObject> T findBy(String field, Object value)
            throws SQLException {

        T el = createElement();
        DataSet res = DBManager.getInstance()
                .select(getTableName(), el.getParams(), field, value);

        el.setFromParams(res.get(0));

        return el;
    }

    public <T extends CatalogObject> List<T> selectAll() throws SQLException {

        T el = createElement();
        DataSet ds = DBManager.getInstance()
                .select(getTableName(), el.getParams());

        List<T> res = new ArrayList<>(ds.size());

        for (Map<String, Object> row : ds) {
            el = createElement();
            el.setFromParams(row);
            res.add(el);
        }

        return res;
    }
}
