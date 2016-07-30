package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.record.DVALRecord;

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
    private static String USERNAME = "";
    private static String PASSWORD = "";

    static final String DB_URL = "jdbc:sqlserver://SSRSERVER\\SSRSQLEXPRESS//:1433;"
            + "databaseName=testdb;";

    public DBHelper() {
        conn = null;
        stat = null;
        sql = null;
        rs = null;

    }

    protected boolean credentialLogin(final String _username, final String _password) {

        USERNAME = _username;
        PASSWORD = _password;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, _username, _password);
            stat = conn.createStatement();
            conn.setAutoCommit(true);

            if (!conn.isClosed()) {
                System.out.println("The Connection is opened and you are logged in " + attempts);

                /*int j=0;
                int k=0;
                for(int i=0;i<200;i++) {
                    j = new Random().nextInt(55);
                    k = new Random().nextInt(55);
                    //sql = "INSERT INTO Inventory VALUES('#Example"+(100+i)+"',"+j+","+k+","+(i%2)+",0,'These are some notes about the example,"+(j+k+(i%2))+",'"+(new Random().nextInt(50)+2012)+"0619');";
                    sql = "INSERT INTO Inventory VALUES('#Example"+(100+i)+"',"+j+","+k+","+(i%2)+",1,'These are some notes aboouhut the example part',"+(j+k+(i%2))+",convert(date,'"+(new Random().nextInt(10)+1)+"-14-"+(new Random().nextInt(40)+2000)+"',101));";
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
                    list.add(new Items(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getInt(7),rs.getDate(8)));
                    System.out.println(rs.getString(1)+" "+rs.getInt(2));
                }


            }
            catch (SQLException e){
            e.printStackTrace();

            }

        SearchBox.setSearchContent(list);
        return list;
    }
}
