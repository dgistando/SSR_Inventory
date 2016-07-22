package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import static inventory.Inventory_Controller.SearchBox;

/**
 * Created by SSR on 6/30/2016.
 */

public class DBHelper{
    static Connection conn = null;
    static Statement stat = null;
    static String sql = null;
    static ResultSet rs;
    static int attempts = 1;

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String USERNAME = "SSR_Janet";
    static final String PASSWORD = "Password#2";

    static final String DB_URL = "jdbc:sqlserver://SSRSERVER\\SSRSQLEXPRESS//:1433;"
            + "databaseName=testdb;";

    public DBHelper() {
        Connection conn = null;
        Statement stat = null;
        String sql = null;
        ResultSet rs = null;
    }

    protected boolean credentialLogin() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            stat = conn.createStatement();

            if (!conn.isClosed()) {
                /*System.out.println("The Connection is opened and you are logged in " + attempts);

                for(int i=0;i<200;i++) {
                    sql = "INSERT INTO Inventory VALUES('#Example"+(100+i)+"',"+new Random().nextInt(55)+",0,"+(i%2)+",0,'"+(2016+i)+"-07-5','These are some notes about the example"+(i+new Random().nextInt(85))+"part');";
                    stat.executeUpdate(sql);
                }*/
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Login failed" + attempts);
            e.printStackTrace();
            return false;
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Login failed" + attempts);
            e.printStackTrace();
            return false;
            //e.printStackTrace();
        } finally {
            attempts++;
        }
        return false;
    }

    public ObservableList<Items> getAllItems() {
        ObservableList<Items> list = FXCollections.observableArrayList();

        try {
                sql = "SELECT * FROM inventory;";
                rs = stat.executeQuery(sql);

                while(rs.next()){
                    list.add(new Items(rs.getString(1),rs.getInt(2),rs.getBoolean(3),rs.getBoolean(4),rs.getBoolean(5),rs.getDate(6),rs.getString(7)));
                    System.out.println(rs.getString(1)+" "+rs.getString(2));
                }


            }
            catch (SQLException e){
            e.printStackTrace();
        }
        SearchBox.setSearchContent(list);
        return list;
    }
}
