package assignment_2.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLConnection{

    private final String db = "jdbc:mysql://localhost:3306/playlistdb";
    private final String usr = "myuser";
    private final String pwd = "xxxx";

    private static Properties properties;
    
    public SQLConnection (){
        properties = new Properties();
        properties.setProperty("user", usr);
        properties.setProperty("password", pwd);
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection createConnection(){
        try{
            return DriverManager.getConnection(db, properties);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}