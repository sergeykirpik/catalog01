package kirpiks.erp;

import kirpiks.erp.conf.catalogs.CustomerManager;
import kirpiks.erp.conf.catalogs.CustomerObject;
import kirpiks.erp.conf.catalogs.ProductManager;
import kirpiks.erp.conf.catalogs.ProductObject;
import kirpiks.erp.db.DBManager;

import java.sql.*;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("CatalogManager Application");

        try {
            //createTables();
            //dropTables();
            //createProducts();
            //createCustomers();
            dropTables();
            createTables();
            createCustomers2();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            DBManager.getInstance().close();
        }
    }

    static void createCustomers2() throws SQLException {
        CustomerObject c1 = CustomerManager.getInstance().createElement();

        c1.setCode(1);
        c1.setDescription("Customer 01");
        c1.setAddress("Pushkina St");
        c1.save();

        c1.setAddress("Bol. Arnautskaya St");
        c1.save();

        c1.setDescription("Some description");
        System.out.println("Before read(): " + c1.getDescription());
        c1.read();

        System.out.println("After read(): " + c1.getDescription());
    }

    static void createCustomers() throws SQLException {
        CustomerManager mgr = CustomerManager.getInstance();

        CustomerObject c1 = mgr.createElement();
        c1.setDescription("Vasya");
        c1.setAddress("123 Lenin St");
        mgr.save(c1);

        CustomerObject c2 = mgr.createElement();
        c2.setDescription("Kolya");
        c2.setAddress("564 Suvorov St");
        mgr.save(c2);
    }

    static void createProducts() throws SQLException {
        ProductManager mgr = ProductManager.getInstance();
        ProductObject el = mgr.createElement();
        el.setDescription("My Product");

        StringBuilder sb = new StringBuilder();
        sb.append(el.getRef() + ": ");
        sb.append(el.getDescription());
        System.out.println(sb);

        mgr.save(el);
    }

    static void createTables() throws SQLException {

        CustomerManager.getInstance().createTable();
        ProductManager.getInstance().createTable();
    }

    static void dropTables() throws SQLException {
        CustomerManager.getInstance().dropTable();
        ProductManager.getInstance().dropTable();
    }

}

