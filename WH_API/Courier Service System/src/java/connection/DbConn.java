/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author chanw
 */
public class DbConn {
    private static final String url = "jdbc:mysql://localhost:3306/courier?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String user = "root";
    private static final String password = "";
    
    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("connected");
        }
        return connection;
    }
}
