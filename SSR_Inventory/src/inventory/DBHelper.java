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

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String USERNAME = "";
    static final String PASSWORD = "";

    static final String DB_URL = "jdbc:sqlserver://SSR-JANET\\SQLEXPRESS//:1343;"
                                + "databaseName=testdb;"
                                + "integratedSecurity=true;";

    public DBHelper(){
        try{

            if(!conn.isClosed()){
                return;
            }

            Class.forName(JDBC_DRIVER);
            DriverManager.getConnection("");

        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
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
