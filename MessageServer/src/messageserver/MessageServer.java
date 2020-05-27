/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 26/05/2020
 * Java III
 * Project AT3 Question 3
 * 
 * inter process communication
 * server application 
 * main module
 * 
 */

public class MessageServer extends Application {

    // testing flag
    // set to true to see progress messages from within the code
    public static boolean testingFlag = true;
    // disconnect string
    public static final String DISCONNECT = "closeConnection";
    // login attempt outcome
    public static final String LOGIN_PASS = "pass";
    public static final String LOGIN_FAIL = "fail";
    
    // admin user name and password
    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PW = "adminPw";
    // system message lable
    public static final String SYS_USER = "sys: ";
    
    
    // declare global interface elements
    // allows access to the elements from anywhere in the program
    
    // accessable stage object
    Stage mainStage;
    
    // list and list view objects
    // list of client records
    private static ObservableList<String> clientList = 
            FXCollections.observableArrayList();

    // list of client received messages
    private static ObservableList<String> messageList = 
            FXCollections.observableArrayList();

    // the client manager object
    private static ClientList ClientManager = new ClientList( clientList);
    
    // port number for socket
    public static final int PORT_NUMBER = 8081;

    // instantiate the listener object
    // private static ListenForClient listener 
    //         = new ListenForClient( ClientManager, PORT_NUMBER);

    public static void main(String[] args) {
        // start the GUI application
        Application.launch(args);

    }
    
    @Override
    // setup and run the GUI
    public void start(Stage primaryStage) {
        // set the accessable stage
        mainStage = primaryStage;
        
        // in testing mode create test users
        if(testingFlag)
            // ClientManager.AddTestUsers();
        
        // application title
        primaryStage.setTitle("Message server");

        // create a grid container to hold the interface elements
        GridPane layoutGrid = new GridPane();
        
        // build the GUI
        MainForm.buildGui( layoutGrid, clientList, messageList);
        
        // set the scene
        Scene scene = new Scene( layoutGrid);
        // display the form
        primaryStage.setScene(scene);
        primaryStage.show();

        // load the users
        DataStore.LoadUsers(ClientManager);
        
    }
    
    // create a new user
    public static void CreateNewUser()
    {        
        // declare local variables
        String newName;
        String newPw;
        String message;

        // try to read values from the form
        try {
            // read the user name
            newName = MainForm.GetUserName();
            newPw = MainForm.GetUserPw();
            
            // add the user
            ClientManager.AddClient(newName, newPw);
            
            // cleanup
            MainForm.ClearUserFields();
                    
            // create the message
            message = String.format("new user created %s", 
                    newName);
            
            // display message
            MainForm.ShowReceivedMessage( SYS_USER + message);
            // save a log message
            DataStore.AddLogRecord(message);
            
        }
        catch ( Exception ex)
        {
            MainForm.ShowErrorMessage(ex.getMessage());
            
        }
    
    }
    
    // log a user into the system
    public static void UserLogIn()
    {
        // declare local variables
        String newName;
        String newPw;
        Client testClient;
        Boolean passwordValid;
        String message;
        
        // try to read values from the form
        try {
            // read the user name
            newName = MainForm.GetUserName();
            newPw = MainForm.GetUserPw();
            
            // add the Client object
            testClient = ClientManager.FindNode(newName);

            // test for a null value
            if( testClient != null)
            {
                // test the password
                passwordValid = PassWordManager.CheckPassword( newPw, 
                        testClient.getPasswordHash(),
                        testClient.getPasswordSalt());
                
                // set the active flag
                testClient.setActiveFlag(passwordValid);

                // was the username & password combination valid
                if( passwordValid)
                {
                    // test for an admin login
                    if(newName.equals(ADMIN_USER))
                        MainForm.AdminLogin();

                    // cleanup
                    MainForm.ClearUserFields();
                    
                    // create the message
                    message = String.format("sucessfull test login for %s", 
                            newName);
                    // display message
                    MainForm.ShowReceivedMessage(SYS_USER + message);
                    // record log message
                    DataStore.AddLogRecord(message);
                    
                }
                else
                {
                    // error message invalid password fail
                    // create the error message
                    message = String.format("the password is not valid for %s", 
                            newName);
                    // display message
                    MainForm.ShowErrorMessage( message);
                    // record log message
                    DataStore.AddLogRecord(message);
                    
                }
                
            }
            else
            {
                // error message invalid user name
                // create the error message
                message = String.format("the user name %s could not be found", 
                        newName);
                // display message
                MainForm.ShowErrorMessage( message);
                // record log message
                DataStore.AddLogRecord(message);
                
            }
            
        }
        catch ( Exception ex)
        {
            MainForm.ShowErrorMessage(ex.getMessage());
            
        }
        
        // update the client list
        ClientManager.ListNodes();
        
    }
    
    // log a user out of the system
    public static void UserLogOut()
    {
        // declare local variables
        String newName;
        Client testClient;
        String message;
        
        // try to read values from the form
        try {
            // read the user name
            newName = MainForm.GetUserName();
            
            // add the Client object
            testClient = ClientManager.FindNode(newName);

            // test for a null value
            if( testClient != null)
            {
                // set the active flag to false
                testClient.setActiveFlag(false);

                // test for an admin log out
                if(newName.equals(ADMIN_USER))
                    MainForm.AdminLogout();

                // cleanup
                MainForm.ClearUserFields();
                    
                // create the message
                message = String.format("log out for %s", 
                        newName);
                // display message
                MainForm.ShowReceivedMessage(SYS_USER + message);
                // record log event
                DataStore.AddLogRecord(message);
                
            }
            else
            {
                // error message invalid user name
                // create the error message
                message = String.format("the user name %s could not be found", 
                        newName);
                // display message
                MainForm.ShowErrorMessage( message);
                // record log event
                DataStore.AddLogRecord(message);
                
            }
            
        }
        catch ( Exception ex)
        {
            MainForm.ShowErrorMessage(ex.getMessage());
            
        }
        
        // update the client list
        ClientManager.ListNodes();
        
    }

}
