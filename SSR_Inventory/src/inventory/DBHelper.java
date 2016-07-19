package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by SSR on 6/30/2016.
 */

public class DBHelper {
    static Connection conn = null;
    static Statement stat = null;
    static String sql = null;
    static ResultSet rs;
    static int attempts = 1;

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String USERNAME = "testlogin";
    static final String PASSWORD = "Register#2";

    static final String DB_URL = "jdbc:sqlserver://LAPTOP-3G1FS1AP\\SQLEXPRESS//:1433;"
                                + "databaseName=testdb;"
                                + "integratedSecurity=true;";

    public DBHelper(){
        Connection conn = null;
        Statement stat = null;
        String sql = null;
        ResultSet rs = null;
    }

    protected boolean credentialLogin(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            stat = conn.createStatement();

            if (!conn.isClosed()) {
                System.out.println("The Connection is opened and you are logged in " + attempts);
                return true;
            }

        }catch (SQLException e){
            System.out.println("Login failed"+attempts);
            return false;
            //e.printStackTrace();
        }catch (ClassNotFoundException e){
            System.out.println("Login failed"+attempts);
            return false;
            //e.printStackTrace();
        }finally {
            attempts++;
        }
        return false;
    }

    public ObservableList<Items> getAllItems(){
        ObservableList<Items> list=  null;

        try {
            sql = "SELECT * FROM inventory;";
            rs = stat.executeQuery(sql);

            /*while(rs.next()){
             list = FXCollections.observableList(
                    new Items();
                    );
            }*/
        }
            catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
