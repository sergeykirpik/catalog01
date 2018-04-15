package kirpiks.erp.conf.catalogs;

import kirpiks.erp.meta.CatalogObject;

import java.util.List;
import java.util.Map;

public class CustomerObject extends CatalogObject {

    private String Address;

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = super.getParams();
        params.put("Address", getAddress());

        return params;
    }

    CustomerObject() {}

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

}
