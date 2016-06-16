import java.util.*;
import java.sql.*;

public class Jdbc{

    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:SSR_Inventory";
            //"//home/dgistand/UCMEng_Collaboratory03/Desktop/TPCH";

    static Scanner kbd = new Scanner(System.in);
    static Connection conn = null;
    static Statement stat = null;
    static String sql=null;
    static ResultSet rs;

    public static void main(String[] args){
            Class.forName(JDBC_DRIVER);

    }
}