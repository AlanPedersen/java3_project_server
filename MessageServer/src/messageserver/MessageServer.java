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
    // private static ClientList ClientManager = new ClientList( clientList);
    
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

        // create the admin user
        // ClientManager.AddAdminUser( ADMIN_USER, ADMIN_PW);

        // in testing mode create test users
        if(testingFlag)
            // ClientManager.AddTestUsers();
        
        // application title
        primaryStage.setTitle("IPC server");

        // create a grid container to hold the interface elements
        GridPane layoutGrid = new GridPane();
        
        // build the GUI
        MainForm.buildGui( layoutGrid, clientList, messageList);
        
        // set the scene
        Scene scene = new Scene( layoutGrid);
        // display the form
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }


}
