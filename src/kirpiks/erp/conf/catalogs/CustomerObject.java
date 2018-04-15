package kirpiks.erp.conf.catalogs;

import kirpiks.erp.meta.CatalogObject;

import java.util.Map;

public class CustomerObject extends CatalogObject {

    public static final String COL_ADDRESS = "Address";

    public static Map<String, String> getColTypes() {

        Map<String, String> map = CatalogObject.getColTypes();
        map.put(COL_ADDRESS, "VARCHAR(150)");
        return map;
    }

    private String Address;

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = super.getParams();
        params.put("Address", getAddress());

        return params;
    }

    @Override
    public void setFromParams(Map<String, Object> map) {
        super.setFromParams(map);
        setAddress((String) map.get(COL_ADDRESS));
    }

    CustomerObject() {}

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

}
