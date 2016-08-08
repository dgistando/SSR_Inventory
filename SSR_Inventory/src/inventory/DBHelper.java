package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.record.DVALRecord;
import org.apache.poi.hssf.record.PrecisionRecord;

import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import static inventory.Inventory_Controller.SearchBox;
import static inventory.Inventory_Controller.programInfo;

/**
 * Created by SSR on 6/30/2016.
 */

public class DBHelper{
    static String sql = null;
    static ResultSet rs;
    static int attempts = 1;
    private Queue<String> changes;

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String USERNAME;
    private static String PASSWORD;

    static final String DB_URL = "jdbc:sqlserver://LAPTOP-3G1FS1AP\\SQLEXPRESS//:1433;"
            + "databaseName=testdb;";


    public DBHelper() {
        changes = new PriorityQueue<String>();
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

    public String getUSERNAME(){
        return USERNAME;
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

    public void insertIntoInventory(Items itm){
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = getConn();

            java.sql.Date DateNow = new java.sql.Date(Calendar.getInstance().getTime().getTime());

                sql = "INSERT INTO Inventory VALUES(?,?,?,?,?,?,?,?,?);";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,itm.getCustomLabel());
                preparedStatement.setInt(2,itm.getNetSaleable());
                preparedStatement.setInt(3,itm.getReturns());
                preparedStatement.setInt(4,itm.getDefective());
                preparedStatement.setInt(5,itm.getIncomplete());
                preparedStatement.setString(6,itm.getNotes());
                preparedStatement.setInt(7,itm.getQuantity());
                preparedStatement.setString(8,itm.getPackingInformation());
                preparedStatement.setDate(9,DateNow);

                preparedStatement.executeUpdate();

            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
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

            preparedStatement.executeUpdate();


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

                preparedStatement.executeUpdate();
            }

        conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void newAutoInventory(HashMap<String, String> inventoryPair){

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = getConn();

            java.sql.Date DateNow = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            for(HashMap.Entry<String,String> entry: inventoryPair.entrySet()){
                //sql = "INSERT INTO Inventory VALUES('"+custom_label.get(i)+"',0,0,0,0,'',0,'"+information.get(i)+"',GETDATE());";
                sql = "INSERT INTO Inventory VALUES(?,?,?,?,?,?,?,?,?);";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,entry.getKey());
                preparedStatement.setInt(2,0);
                preparedStatement.setInt(3,0);
                preparedStatement.setInt(4,0);
                preparedStatement.setInt(5,0);
                preparedStatement.setString(6,"");
                preparedStatement.setInt(7,0);
                preparedStatement.setString(8,entry.getValue());
                preparedStatement.setDate(9,DateNow);

                preparedStatement.executeUpdate();
            }

            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getNumChanges(){
        return changes.size();
    }

    public void recordChange(String query){
        changes.add(query);
    }

    public boolean areChangesMade(){
        return changes.isEmpty();
    }

    public void discardChanges(){
        changes.clear();
    }

    public void commitChanges(){
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = getConn();

            while(!changes.isEmpty()){
                System.out.println(changes.peek());
                preparedStatement = conn.prepareStatement(changes.poll());
                preparedStatement.executeUpdate();
            }

            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeEditor(){
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = getConn();


            sql = "UPDATE Editing SET changing=0 WHERE usern=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,USERNAME);
            preparedStatement.executeUpdate();

            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean setUserEditing(){
        if(isUserEditing()){
            return false;
        }

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            java.sql.Date DateNow = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            conn = getConn();

            sql = "UPDATE Editing SET usern=?, edits=?, changetime=? WHERE changing = 0;";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,USERNAME);

                if(!changes.isEmpty()){
                    String str = changes.peek();
                    preparedStatement.setString(2,str.substring(str.lastIndexOf("="),str.lastIndexOf("'")));
                }else{
                    preparedStatement.setString(2,"Inventory");
                }

                preparedStatement.setDate(3,DateNow);
                preparedStatement.executeUpdate();

            //Set chaging to used
            sql = "UPDATE Editing SET changing=1 WHERE usern=?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,USERNAME);
                preparedStatement.executeUpdate();

            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean isUserEditing(){
        //if someone is editing the items are made read only

        Connection conn = null;
        Statement stat = null;

        try {
            conn = getConn();
            stat = conn.createStatement();

            sql = "SELECT * FROM Editing;";
            rs = stat.executeQuery(sql);

            rs.next();
            if(rs.getBoolean(4) && !rs.getString(1).equals(USERNAME)){//its true someone is editing and not me
                conn.close();
                return true;
            }else{
                conn.close();
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

   public String whoEditing(){
        //if someone is editing the items are made read only

        Connection conn = null;
        Statement stat = null;
        String str = "null";

        try {
            conn = getConn();
            stat = conn.createStatement();

            sql = "SELECT * FROM Editing;";
            rs = stat.executeQuery(sql);

            rs.next();
            if(rs.getBoolean(4)){
                str = rs.getString(1) + " is editing " + rs.getString(2).substring(2) + ". ("+ convertTime(rs.getTime(3).getTime()) + ")";
            }else{
                str = "Last edits were made by " + rs.getString(1) + "on " + rs.getString(2).substring(2) + " at "+ convertTime(rs.getDate(3).getTime());
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }

       return str;
    }

    private String convertTime(long timeInMillis){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        return dateFormat.format(cal1.getTime()).substring(dateFormat.format(cal1.getTime()).indexOf(" "));
    }


}
