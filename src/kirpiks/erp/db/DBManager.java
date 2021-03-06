package kirpiks.erp.db;

import java.sql.*;
import java.util.Map;

public class DBManager {

    private static final String USER = "root";
    private static final String PWD = "1111";
    private static final String CONN_STR =
            "jdbc:mysql://localhost/test?allowMultiQueries=true";

    private Connection conn = null;

    private static DBManager instance = null;

    private DBManager() {
        try {
            conn = DriverManager.getConnection(CONN_STR, USER, PWD);
            System.out.println("DBManager: connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void close() {
        if (conn != null) try {
            conn.close();
            System.out.println("DBManager: connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(
            String tableName, Map<String, String> columns)
            throws SQLException {

        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(tableName).append(" ( ");

        boolean isFirst = true;
        for (String col : columns.keySet()) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append(col).append(" ").append(columns.get(col));
        }

        sb.append(" ); ");


        String sql = sb.toString();

        System.out.println(sql);

        try (
                Statement stmt = conn.createStatement()
        ) {

            stmt.executeUpdate(sql);
        }
    }

    public void dropTable(String tableName) throws SQLException {

        String sql = "DROP TABLE IF EXISTS " + tableName;
        System.out.println(sql);

        try (
                Statement stmt = conn.createStatement()
                ) {

            stmt.executeUpdate(sql);
        }
    }

    public void insert(String tableName, Map<String, Object> params) throws SQLException {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(tableName);
        sb.append(" ( ");

        boolean isFirst = true;
        for (String key : params.keySet()) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append(key);
        }
        sb.append(" ) ");
        sb.append("VALUES ( ");

        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(" );");

        String sql = sb.toString();

        System.out.println(sql);

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {

            int num = 1;
            for (String key : params.keySet()) {
                stmt.setObject(num, params.get(key));
                num++;
            }
            System.out.println(stmt);
            int affected = stmt.executeUpdate();

            System.out.println("Rows affected: " + affected);
        }

    }

    public void update(String tableName, Map<String, Object> params, String pkKey) throws SQLException {

        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(tableName).append(" SET ");
        boolean isFirst = true;
        for (String param : params.keySet()) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append(param).append(" = ?");
        }

        sb.append(" WHERE ").append(pkKey).append(" = ?;");

        String sql = sb.toString();
        System.out.println(sql);

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            int num = 1;
            for (Object v : params.values()) {
                stmt.setObject(num, v);
                num++;
            }
            stmt.setObject(num, params.get(pkKey));

            System.out.println(stmt);
            int affected = stmt.executeUpdate();
            System.out.println("Rows affected: " + affected);
        }

    }

    public DataSet select(
            String tableName, Map<String, Object> params)
            throws SQLException {

        return this.select(tableName, params, null, null);
    }

    public DataSet select(
            String tableName, Map<String, Object> params, String field)
            throws SQLException {

        return this.select(tableName, params, field, null);
    }

    public DataSet select(
            String tableName, Map<String, Object> params, String field, Object value)
            throws SQLException {

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(tableName);

        if (field != null) {
            sb.append(" WHERE ").append(field).append(" = ?;");
        }

        String sql = sb.toString();
        System.out.println(sql);

        ResultSet rs = null;

        DataSet results = new DataSet();

        try (
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {

            if (field != null) {
                if (value == null) {
                    stmt.setObject(1, params.get(field));
                } else {
                    stmt.setObject(1, value);
                }
            }
            System.out.println(stmt);
            rs = stmt.executeQuery();

            while (rs.next()) {
                results.newRow();
                for (String key : params.keySet()) {
                    results.put(key, rs.getObject(key));
                }
            }
            return results;

        } finally {
            if (rs != null) rs.close();
        }
    }

}
