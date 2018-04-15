package kirpiks.erp.conf.catalogs;

import kirpiks.erp.meta.CatalogManager;

import java.util.List;

public class CustomerManager extends CatalogManager {

    private static CustomerManager instance = null;

    private CustomerManager() { }

    public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    @Override
    public String getTableName() {
        return "Customers";
    }

    @Override
    public List<String> getTableColumns() {

        List<String> columns = super.getTableColumns();
        columns.add("Address VARCHAR(100)");

        return columns;
    }

    @Override
    protected CustomerObject createConcreteElement() {
        return new CustomerObject();
    }
}
