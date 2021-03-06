package com.company;

import java.sql.*;

public class Main {

    static Connection conn = null;
    static Statement stat = null;
    static String sql = null;
    static ResultSet rs;

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String USERNAME = "sa";
    static final String PASSWORD = "newpassword";

    static final String DB_URL = "jdbc:sqlserver://USERNAME\\SQLEXPRESS//:1343;"
            + "databaseName=dbname;"
            + "integratedSecurity=true;";

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            stat = conn.createStatement();
            if(!conn.isClosed()){
                System.out.println("Connection Open\n");
            }

            sql = "select * from testtable;";
            rs = stat.executeQuery(sql);

            while(rs.next()){
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
            }


            conn.close();
            if(conn.isClosed()){
                System.out.println("\nConnection Closed");
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}

//http://www.javaworld.com/article/2076698/core-java/add-an-undo-redo-function-to-your-java-apps-with-swing.html?page=2
