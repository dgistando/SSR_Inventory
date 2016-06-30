package inventory;

import java.sql.*;
import java.io.*;
/**
 * Created by SSR on 6/30/2016.
 */
public class DBHelper {
    static Connection conn = null;
    static Statement stat = null;
    static String sql = null;
    static ResultSet rs;

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String USERNAME = "";
    static final String PASSWORD = "";

    static final String DB_URL = "jdbc:sqlserver://SSR-JANET\\SQLEXPRESS//:1343;"
                                + "databaseName=testdb;"
                                + "integratedSecurity=true;";

    public DBHelper(){
        try{
            Class.forName(JDBC_DRIVER);
            DriverManager.getConnection("");

        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
