/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import java.sql.*;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 09/05/2020
 * Java III
 * Portfolio AT2 Question 4
 * 
 * inter process communication
 * server application 
 * code to interact with the database
 * 
 */

public class DataStore {
    // declare global variables
    // database connection variables
    static final String DB_URL = "jdbc:mysql://localhost/java3_project";
    static final String USER = "root";
    static final String PASS = "";
    
    // get a connection to the database
    private static Connection GetConnection() throws Exception
    {
        return DriverManager.getConnection(DB_URL, USER, PASS);
        
    }
    
    // read user records from the database
    public static void LoadUsers( ClientList ClientManager)
    {
        // declare local variables
        // connection & statements objects
        Connection conn = null;
        Statement stmt = null;
        // results from user query
        ResultSet clientRecords;
        // SQL statement string
        String sqlStatement;
        // string version of the salt value
        String saltString;
        
        try
        {
            // get the connection to the database
            conn = GetConnection();
            // setup the insert statement
            sqlStatement = "SELECT userName, userSalt, userHash FROM user_login";
            
            // create the statement object
            stmt = conn.createStatement();
            
            // run the query and get the results
            clientRecords = stmt.executeQuery( sqlStatement);
            
            // test that some records were loaded
            if( clientRecords.next())
            {    
                // found records
                // loop through the results record set
                do
                {
                    ClientManager.LoadClient(clientRecords.getString("userName"), 
                            clientRecords.getString("userSalt"),
                            clientRecords.getString("userHash"));

                } while( clientRecords.next());
            }
            else
            {
                // no records found
                // create the default admin account
                ClientManager.AddClient(MessageServer.ADMIN_USER, 
                        MessageServer.ADMIN_PW);
                
                // record a log event
                AddLogRecord("default admin user created");
                
            }

            // close the statement object
            stmt.close();
            // close the connection
            conn.close();
            
        }
        catch( Exception ex)
        {
            // display the error message
            MainForm.ShowErrorMessage(ex.getMessage());
            
        }
        finally
        {
            try{
                if( stmt != null)
                    stmt.close();

                if( conn != null)
                    conn.close();
                
            }
            catch( Exception ex)
            {
            // display the error message
            MainForm.ShowErrorMessage(ex.getMessage());
                
            }
        }        
        
    }
    
    // add a user to the database
    public static void NewUser( Client newClient)
    {
        // declare local variables
        // connection & statements objects
        Connection conn = null;
        PreparedStatement stmt = null;
        // SQL statement string
        String sqlStatement;
        // string version of the salt value
        String saltString;

        try
        {
            // get the connection to the database
            conn = GetConnection();
            // setup the insert statement
            sqlStatement = "INSERT INTO user_login ( userName, userSalt, userHash) "
                         + "VALUES ( ?, ?, ?)";
            
            // create the statement object
            stmt = conn.prepareStatement(sqlStatement);
            
            // set the values in the prepared statment
            stmt.setString(1, newClient.getName());
            stmt.setString(2, newClient.getPasswordSalt());
            stmt.setString(3, newClient.getPasswordHash());
            // execute the statement
            stmt.execute();

            // close the statement object
            stmt.close();
            // close the connection
            conn.close();
            
        }
        catch( Exception ex)
        {
            // display the error message
            MainForm.ShowErrorMessage(ex.getMessage());
            
        }
        finally
        {
            try{
                if( stmt != null)
                    stmt.close();

                if( conn != null)
                    conn.close();
                
            }
            catch( Exception ex)
            {
            // display the error message
            MainForm.ShowErrorMessage(ex.getMessage());
                
            }
        }        
    }
    
    // add a record to the log table
    public static void AddLogRecord( String log)
    {
        // declare local variables
        // connection & statements objects
        Connection conn = null;
        PreparedStatement stmt = null;
        // SQL statement string
        String sqlStatement;

        try
        {
            // get the connection to the database
            conn = GetConnection();
            // setup the insert statement
            sqlStatement = "INSERT INTO log_table ( logMessage) "
                         + "VALUES ( ?)";
            // create the statement object
            stmt = conn.prepareStatement(sqlStatement);
            // set the values in the prepared statment
            stmt.setString(1, log);
            // execute the statement
            stmt.execute();

            // close the statement object
            stmt.close();
            // close the connection
            conn.close();
            
        }
        catch( Exception ex)
        {
            // display the error message
            MainForm.ShowErrorMessage(ex.getMessage());
            
        }
        finally
        {
            try{
                if( stmt != null)
                    stmt.close();

                if( conn != null)
                    conn.close();
                
            }
            catch( Exception ex)
            {
            // display the error message
            MainForm.ShowErrorMessage(ex.getMessage());
                
            }
        }        
    }
    
    
}
