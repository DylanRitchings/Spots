package com.dylanritchings.Database;

import ;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dylan Richings
 */
public class loginConnect {
    
    private static String host = "";
    private static String uName = "";
    private static String dbname = "";
    private static Integer portnumber = 3306;
    private static String uPass = "";
    
    
    public static Connection getConnection()
    {
        Connection cnx = null;
        
        MysqlDataSource datasource = new MysqlDataSource();
        
        datasource.setServerName(host);
        datasource.setUser(uName);
        datasource.setPassword(uPass);
        datasource.setDatabaseName(dbname);
        datasource.setPortNumber(portnumber);
                
        try {
            cnx = datasource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(loginConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cnx;
    }       
    
}