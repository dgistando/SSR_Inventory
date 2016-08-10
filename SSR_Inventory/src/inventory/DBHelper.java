package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
 * This class is for connecting to the SSR SQL Server. The
 *
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

    static final String DB_URL = "jdbc:sqlserver://SSRSERVER\\SSRSQLEXPRESS/:1433;"
            + "databaseName=SSRInventory;";


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

        Connection dbConnection = null;
        Statement stat = null;
        try {
            Class.forName(JDBC_DRIVER);
            dbConnection = DriverManager.getConnection(DB_URL,"loginManager","testlogin#3");
            stat = dbConnection.createStatement();

            if (!dbConnection.isClosed()) {
                System.out.println("The Connection is opened " + attempts);

                rs = stat.executeQuery("SELECT * FROM Users WHERE username='" + _username + "';");
                while (rs.next()) {

                    if (_password.equals(rs.getString(3))) {
                        System.out.println("Password match" + attempts);
                        dbConnection.close();
                        return true;
                    }

                    dbConnection.close();
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Login failed" + attempts);
            e.printStackTrace();
            return false;
            //e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
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

    public ObservableList<NewInventory> getReceivingSheets(){
        ObservableList<NewInventory> list = FXCollections.observableArrayList();

        Connection conn = null;
        Statement stat = null;

        try {
            conn = getConn();
            stat = conn.createStatement();

            sql = "SELECT * FROM ReceivingSheets;";
            rs = stat.executeQuery(sql);

            while(rs.next()){
                list.add(new NewInventory(rs.getString(1), rs.getDate(2).toString(), rs.getString(3), rs.getBoolean(4)));
                //System.out.println(rs.getString(1)+" "+rs.getInt(2));
            }

            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }


        return list;
    }

    public void addReceivingSheet(NewInventory sheet){
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            //adding individual sheet
            sql="INSERT INTO ReceivingSheets VALUES(?,?,?,?);";
            conn = getConn();
            preparedStatement = conn.prepareStatement(sql);

            java.sql.Date added = new java.sql.Date(sheet.getDateInFormat().getTime());

            preparedStatement.setString(1,sheet.getSupplier());
            preparedStatement.setDate(2,added);
            preparedStatement.setString(3,String.valueOf(sheet.getInvoice()));
            preparedStatement.setBoolean(4,sheet.isVerified());
            preparedStatement.executeUpdate();


            for(int i=0;i<sheet.getCustomLabel().size();i++){
                sql = "INSERT INTO Receiving VALUES(?,?,?,?,?,?,?);";
                preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1,sheet.getSupplier());
                preparedStatement.setString(2,sheet.getCustomLabel().get(i));
                preparedStatement.setDate(3,added);
                preparedStatement.setInt(4,sheet.getQuantity().get(i));
                preparedStatement.setInt(5,sheet.getNetSaleable().get(i));
                preparedStatement.setInt(6,sheet.getIncomplete().get(i));
                preparedStatement.setInt(7,sheet.getDefective().get(i));

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
        return !changes.isEmpty();
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

            sql = "UPDATE Editing SET usern=?, edits=? WHERE changing = 0;";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,USERNAME);

                if(!changes.isEmpty()){
                    String str = changes.peek();
                    preparedStatement.setString(2,str.substring(str.lastIndexOf("="),str.lastIndexOf("'")));
                }else{
                    preparedStatement.setString(2,"Inventory");
                }

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

    public String usernameOfEditor(){
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
                str=rs.getString(0);
            }else{
                str="null";
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

    private void alertDialog(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Error");
        alert.setContentText("Database access error!");


// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Stacktrace :");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }



}
