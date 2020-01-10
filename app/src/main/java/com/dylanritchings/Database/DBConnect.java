/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dylanritchings.Database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
*Used to create a connection to the database.
*@since 1/3/2019
*@version 1.3
*@author Dylan Ritchings,
*/


public class DBConnect {


    /**
     * Creates a connection to the database
     * @return Connection
     *
     */
    public static Connection databaseConnect()
    {


        try {
            String host = "jdbc:mysql://den1.mysql3.gear.host:3306/teammanagerdb";
            String uName = "teammanagerdb";
            String uPass = "Bc85NMS--V6h";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            return con;

        }
            catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return null;
        }

    }

    /**
     * Executes statement to database.
     * @param query the query that is going to be made to the database
     */
    public static void databaseInput(String query)
    {
        try{

            Connection con = databaseConnect();
            Statement stat = con.createStatement();

            stat.executeUpdate(query);
            stat.close();
        }
        catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());

        }

    }

    /**
     * Returns the ResultSet of a given query
     * @param query the query that is going to be made to the database
     * @return ResultSet
     */
    public static ResultSet databaseSelect(String query)
    {

        Connection con = databaseConnect();
        try {
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }


}