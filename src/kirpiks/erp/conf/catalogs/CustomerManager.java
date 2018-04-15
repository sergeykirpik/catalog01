package kirpiks.erp.conf.catalogs;

import kirpiks.erp.meta.CatalogManager;

import java.util.Map;

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
    public Map<String, String> getColTypes() {
        return CustomerObject.getColTypes();
    }

    @Override
    protected CustomerObject createConcreteElement() {
        return new CustomerObject();
    }
}
