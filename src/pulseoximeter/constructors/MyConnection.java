/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pulseoximeter.constructors;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MyConnection {
public Connection connection;
 
    public Connection getConnection(){
        String dbName = "oximeter analysis";
        String userName = "root";
        String password = "";
    try{
        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://localhost/";
    
        connection = DriverManager.getConnection(connectionURL+dbName,userName,password);
    } catch (ClassNotFoundException | SQLException e){
        
    }
        return connection;
}}

/*
   public Connection getConnection(){
        String dbName = "epiz_30830457_oximeter_analysis";
        String userName = "epiz_30830457";
        String password = "w3k06IDptf";
    try{
        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://sql209.epizy.com:3306/";
    
        connection = DriverManager.getConnection(connectionURL+dbName,userName,password);
    } catch (ClassNotFoundException | SQLException e){
        
    }
        return connection;
}}*/