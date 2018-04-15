package kirpiks.erp.conf.catalogs;

import kirpiks.erp.meta.CatalogManager;

import java.util.List;

public class ProductManager extends CatalogManager {

    private static ProductManager instance = null;

    private ProductManager() {}

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    @Override
    public String getTableName() {
        return "Products";
    }

    @Override
    protected ProductObject createConcreteElement() {
        return new ProductObject();
    }

}
