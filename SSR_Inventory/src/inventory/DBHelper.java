package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.record.DVALRecord;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static inventory.Inventory_Controller.SearchBox;

/**
 * Created by SSR on 6/30/2016.
 */

public class DBHelper{
    static String sql = null;
    static ResultSet rs;
    static int attempts = 1;

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String USERNAME;
    private static String PASSWORD;

    static final String DB_URL = "jdbc:sqlserver://SSRSERVER\\SSRSQLEXPRESS//:1433;"
            + "databaseName=testdb;";


    public DBHelper() {
        sql = null;
        rs = null;
    }

    private static Connection getConn(){

        Connection dbConnection = null;
         try{
             Class.forName(JDBC_DRIVER);
             dbConnection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
             dbConnection.setAutoCommit(true);
         }catch (ClassNotFoundException e){
             e.printStackTrace();
         }catch (SQLException e){
             e.printStackTrace();
         }

        return dbConnection;
    }


    protected boolean credentialLogin(final String _username, final String _password) {
            USERNAME = _username;
            PASSWORD = _password;

        Connection conn = null;
        try {
            conn = getConn();



            if (!conn.isClosed()) {
                System.out.println("The Connection is opened and you are logged in " + attempts);

                /*
                int j=0;
                int k=0;
                for(int i=0;i<5;i++) {
                    j = new Random().nextInt(55);
                    k = new Random().nextInt(55);
                    //sql = "INSERT INTO Inventory VALUES('#Example"+(100+i)+"',"+j+","+k+","+(i%2)+",0,'These are some notes about the example,"+(j+k+(i%2))+",'"+(new Random().nextInt(50)+2012)+"0619');";
                    sql = "INSERT INTO Inventory VALUES('#Example"+(100+i)+"',"+j+","+k+","+(i%2)+",1,'These are some notes aboouhut the example part',"+(j+k+(i%2))+",'',convert(date,'"+(new Random().nextInt(10)+1)+"-14-"+(new Random().nextInt(40)+2000)+"',101));";
                    stat.executeUpdate(sql);
                }*/
                conn.close();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Login failed" + attempts);
            e.printStackTrace();
            return false;
            //e.printStackTrace();
        }finally {
            attempts++;
        }
        return false;
    }

    public ObservableList<Items> getAllItems() {
        ObservableList<Items> list = FXCollections.observableArrayList();

        Connection conn = null;
        Statement stat = null;

        try {
                conn = getConn();
                stat = conn.createStatement();

                sql = "SELECT * FROM inventory;";
                rs = stat.executeQuery(sql);

                while(rs.next()){
                    list.add(new Items(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getInt(7),rs.getString(8),rs.getDate(9)));
                    //System.out.println(rs.getString(1)+" "+rs.getInt(2));
                }

            conn.close();
            }
            catch (SQLException e){
            e.printStackTrace();

            }

        SearchBox.setSearchContent(list);
        return list;
    }

    public ObservableList<Sales> getSalesSheets(){
        ObservableList<Sales> list = FXCollections.observableArrayList();

        Connection conn = null;
        Statement stat = null;

        try {
            conn = getConn();
            stat = conn.createStatement();

            sql = "SELECT * FROM SalesSheets;";
            rs = stat.executeQuery(sql);

            while(rs.next()){
               list.add(new Sales(rs.getString(1),rs.getDate(2).toString(),rs.getString(3).charAt(0),rs.getInt(4)));
                //System.out.println(rs.getString(1)+" "+rs.getInt(2));
            }

            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }


        return list;
    }


    public void addSalesSheet(Sales sheet){
        //prepared statements
        Connection conn = null;
        Statement stat = null;
        PreparedStatement preparedStatement = null;

        try {
            //adding individual sheet
            //sql = "INSERT INTO SalesSheets VALUES('"+sheet.getSource()+"',CONVERT(date,'"+sheet.getDate().replaceAll("\\.","-")+"',101),'"+sheet.getPart()+"',"+sheet.getAllQuantity()+");";
            sql="INSERT INTO SalesSheets VALUES(?,?,?,?);";
            conn = getConn();
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1,sheet.getSource());
            preparedStatement.setDate(2,new java.sql.Date(sheet.getDateInFormat().getTime()));
            preparedStatement.setString(3,String.valueOf(sheet.getPart()));
            preparedStatement.setInt(4,sheet.getTotal());

            stat.executeUpdate(sql);


            for(int i=0;i<sheet.getItemCode().size();i++){
              //  sql = "INSERT INTO Sales VALUES('"+sheet.getFirstname().get(i) +"','"+sheet.getLastname().get(i) +"','"+sheet.getCountry().get(i)+"','"+sheet.getItemCode().get(i)+"',"+sheet.getQuantity().get(i)+",'"+sheet.getPart()+"',CONVERT(date,'"+sheet.getDate().replaceAll("\\.","-")+"',101));";
                sql = "INSERT INTO Sales VALUES(?,?,?,?,?,?,?);";
                preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1,sheet.getFirstname().get(i));
                preparedStatement.setString(2,sheet.getLastname().get(i));
                preparedStatement.setString(3,sheet.getCountry().get(i));
                preparedStatement.setString(4,sheet.getItemCode().get(i));
                preparedStatement.setInt(5,sheet.getQuantity().get(i));
                preparedStatement.setString(6,String.valueOf(sheet.getPart()));
                preparedStatement.setDate(7,new java.sql.Date(sheet.getDateInFormat().getTime()));

                stat.executeUpdate(sql);
            }

        conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void newAutoInventory(HashMap<String, String> inventoryPair){

        Connection conn = null;
        Statement stat = null;

        try {
            conn = getConn();
            stat = conn.createStatement();

            for(int i=0;i<inventoryPair.size();i++){
                sql = "INSERT INTO Inventory VALUES('"+custom_label.get(i)+"',0,0,0,0,'',0,'"+information.get(i)+"',GETDATE());";
                stat.executeUpdate(sql);
            }

            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
