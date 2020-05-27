/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 09/05/2020
 * Java III
 * Project AT3 Question 3
 * 
 * inter process communication
 * server application 
 * code to build and manage the help form
 * 
 */

public class HelpForm {

    public static Stage MakeHelpForm()
    {
        // declare variables
        Stage helpStage = new Stage();
        GridPane layoutGrid = new GridPane();
        layoutGrid.setPadding(new Insets( 25, 25, 25, 25));
        
        // set the title for the help window
        helpStage.setTitle("Server Application Help");
        
        // server help header
        Text serverHelpHeader = new Text("Server Application Help");
        serverHelpHeader.setFont( Font.font("Sans Serif",FontWeight.BOLD,16));
        layoutGrid.add(serverHelpHeader, 0, 0);

        // server start header
        Text serverStartHeader = new Text("\nStarting the server");
        serverStartHeader.setFont( Font.font("Sans Serif",FontWeight.BOLD,12));
        layoutGrid.add(serverStartHeader, 0, 1);
        
        String serverStart = "to start the server click on the 'start server' button\n"
                           + "the 'socket status' label will update to conform\n"
                           + "that the server is running and display the port number\n\n";
        Text serverText = new Text(serverStart);
        serverText.setFont( Font.font("Sans Serif",FontWeight.NORMAL,12));
        layoutGrid.add(serverText, 0, 2);

        // server start header
        Text serverUserHeader = new Text("User Controls");
        serverUserHeader.setFont( Font.font("Sans Serif",FontWeight.BOLD,12));
        layoutGrid.add(serverUserHeader, 0, 3);
        
        String serverUser = "The user controls list the current users\n"
                          + "The active flag will be set to true if the user is logged in\n\n"
                          + "To login enter the user name and password and click "
                          + "the 'login' button\n\n"
                          + "To log out enter the user name and click "
                          + "the 'logout' button\n\n"
                          + "To log out enter the user name and password and click  "
                          + "the 'new user' button\n\n"
                          + "To disconnect a user select the user name and click  "
                          + "the 'disconnect' button\n\n" ;
        Text userText = new Text(serverUser);
        userText.setFont( Font.font("Sans Serif",FontWeight.NORMAL,12));
        layoutGrid.add(userText, 0, 4);

        // server start header
        Text serverMessageHeader = new Text("Message Controls");
        serverMessageHeader.setFont( Font.font("Sans Serif",FontWeight.BOLD,12));
        layoutGrid.add(serverMessageHeader, 0, 5);
        
        String serverMessage = "Messaged are dispayed in the 'messagesreceived' "
                             + "list.\nThe message text will include the source of "
                             + "the message.\n\n"
                             + "To send a message:\n"
                             + "enter the messge in the 'send box' control\n"
                             + "and click the 'send' button\n"
                             + "the message will be sent to all active clients";
        Text messageText = new Text(serverMessage);
        messageText.setFont( Font.font("Sans Serif",FontWeight.NORMAL,12));
        layoutGrid.add(messageText, 0, 6);
        
        Scene scene = new Scene( layoutGrid, 500, 600);
        helpStage.setScene(scene);
        
        return helpStage;
        
    }
    
}
