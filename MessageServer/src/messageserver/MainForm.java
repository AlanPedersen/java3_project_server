/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 09/05/2020
 * Java III
 * Project AT3 Question 3
 * 
 * inter process communication
 * server application 
 * code to build and manage the main form
 * 
 */



public class MainForm {
    // list and list view objects
    // list view object to display the list of client records
    private static final ListView<String> clientListView = 
            new ListView<String>();

    // list of client received messages
    // list view object to display the list of received messages
    private static final ListView<String> messageListView = 
            new ListView<String>();
    // expose the message list object
    private static ObservableList<String> messageList; 

    // text field elements
    // pipe server name
    private static TextField tfPipeName = new TextField("not running");
    // user name input box
    private static TextField tfUserName = new TextField();
    // password input box
    private static TextField tfPassword = new PasswordField();
    // message input area
    private static TextArea tfMessage = new TextArea();
    // message received label
    private static Text messLabel = new Text("OK");
    
    // buttons
    // start the server
    private static Button btnStartServer = new Button("start server");
    // login button
    private static Button btnLogIn = new Button("login");
    // log out button
    private static Button btnLogOut = new Button("log out");
    // create a new user button
    private static Button btnNewUser = new Button("new user");
    // button to disconnect a user
    private static Button btnDisconnect = new Button("disconnect");
    // clear received messages
    private static Button btnClearReceivedMessages = new Button("clear");
    // clear send messages box
    private static Button btnClearSendMessages = new Button("clear");
    // send a message
    private static Button btnSendMessage = new Button("send");
    // close the application
    private static Button btnClose = new Button("close");

    // build the GUI
    public static void buildGui(GridPane layoutGrid, 
            ObservableList<String> clientList,
            ObservableList<String> newMessageList)
    {
        // initialise the grid container for the interface elements
        layoutGrid.setAlignment( Pos.CENTER);
        layoutGrid.setHgap(10);
        layoutGrid.setVgap(10);
        layoutGrid.setPadding(new Insets( 25, 25, 25, 25));
        
        // set the reference to the message list
        messageList = newMessageList;
        
        // add the server controls
        addServerControlsToGui(layoutGrid);
        
        // add the user controls
        addUserControlsToGui(layoutGrid, clientList);
        
        // add the message controls
        addMessageControlsToGui(layoutGrid, newMessageList);
        
        // add the close button
        addCloseToGui(layoutGrid);
        
        // add the event listeners to the form
        addEventListeners();
        
    }
    
    // add the server controls to the GUI
    private static void addServerControlsToGui(GridPane layoutGrid)
    {
        // create the server controls layour grid
        GridPane pipePane = new GridPane();
        // set the spacing between the grid elements and the border padding
        pipePane.setHgap(10);
        pipePane.setVgap(10);
        pipePane.setPadding(new Insets( 10, 5, 5, 5));
        
        // set the grid border
        // create the border stroke
        BorderStroke pipeBS = new BorderStroke( Color.BLACK, 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(5), new BorderWidths(1));
        pipePane.setBorder( new Border( pipeBS));

        // pipe pane label
        Text pipeHeader = new Text("server controls");
        // pipe field label
        Text pipeLabel = new Text("socket status:");
        // add text to the grid
        pipePane.add( pipeHeader, 0, 0);
        pipePane.add( pipeLabel, 0, 1);
        
        // set the text field width
        tfPipeName.setPrefWidth( 200);
        // set the text field to read only
        tfPipeName.setDisable(true);
        
        // add the text field
        pipePane.add( tfPipeName, 1, 1);
        // add the start server button
        pipePane.add( btnStartServer, 1, 2);

        // right align the start button
        GridPane.setHalignment( btnStartServer, HPos.RIGHT);
        // set the button width
        btnStartServer.setPrefWidth(80);
        
        // add the server controls to the main grid
        layoutGrid.add( pipePane, 0, 0);
        
    }
    
    // add the server controls to the GUI
    private static void addUserControlsToGui(GridPane layoutGrid, 
            ObservableList<String> clientList)
    {
        // create the user controls laytout grid
        GridPane userPane = new GridPane();
        // set the spacing between the grid elements and the border padding
        userPane.setHgap(10);
        userPane.setVgap(10);
        userPane.setPadding(new Insets( 10, 5, 5, 5));
        
        // set the grid border
        // create the border stroke
        BorderStroke userBS = new BorderStroke( Color.BLACK, 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(5), new BorderWidths(1));
        userPane.setBorder( new Border( userBS));

        // user pane label
        Text userHeader = new Text("user controls");
        // user name label
        Text userName = new Text("name:");
        // user password label
        Text userPassword = new Text("password:");
    
        // add the pane label
        userPane.add(userHeader, 0, 0, 3, 1);
        
        // attach the client list to the list view
        clientListView.setItems(clientList);
        
        // add the client list view
        userPane.add( clientListView, 0, 1, 3, 1);
        
        // add the name label, text field & login button
        userPane.add( userName, 0, 2);
        userPane.add( tfUserName, 1, 2);
        userPane.add( btnLogIn, 2, 2);

        // add the password label, text field & logout button
        userPane.add( userPassword, 0, 3);
        userPane.add( tfPassword, 1, 3);
        userPane.add( btnLogOut, 2, 3);

        // add the disconnect & new user button
        userPane.add( btnDisconnect, 1, 4);
        userPane.add( btnNewUser, 2, 4);

        // right align the disconnect button
        GridPane.setHalignment( btnDisconnect, HPos.RIGHT);
        // set the button widths
        btnLogIn.setPrefWidth(80);
        btnLogOut.setPrefWidth(80);
        btnDisconnect.setPrefWidth(80);
        btnNewUser.setPrefWidth(80);
        // disable the new user button
        btnNewUser.setDisable(true);
        // disable the disconnect button
        btnDisconnect.setDisable(true);
        
        // set the list width
        clientListView.setPrefWidth(300);

        // add the user controls to the main grid
        layoutGrid.add( userPane, 0, 1);
        
    }
    
    // add the message controls to the GUI
    private static void addMessageControlsToGui(GridPane layoutGrid, 
            ObservableList<String> messageList)
    {
        // create the message controls grid
        GridPane messPane = new GridPane();
        // set the spacing between the grid elements and the border padding
        messPane.setHgap(10);
        messPane.setVgap(10);
        messPane.setPadding(new Insets( 10, 5, 5, 5));
        
        // set the grid border
        // create the border stroke
        BorderStroke messBS = new BorderStroke( Color.BLACK, 
                BorderStrokeStyle.SOLID, 
                new CornerRadii(5), new BorderWidths(1));
        messPane.setBorder( new Border( messBS));

        // pipe pane label
        Text messHeader = new Text("messages");
        // add text to the grid
        messPane.add( messHeader, 0, 0, 3, 1);

        // message received label
        Text messRecLabel = new Text("messages received:");
        // add text to the grid
        messPane.add( messRecLabel, 0, 1, 2, 1);
        // add the message clear button
        messPane.add( btnClearReceivedMessages, 2, 1);
        
        // tie the message list to the view object
        messageListView.setItems(messageList);
        
        // add the message received list
        messPane.add( messageListView, 0, 2, 3, 1);

        // message received label
        Text messSendLabel = new Text("send box:");
        // add text to the grid
        messPane.add( messSendLabel, 0, 4);
        // create an Hbox for the buttons
        HBox messButBox = new HBox();
        // add the buttons
        messButBox.getChildren().addAll( btnSendMessage, btnClearSendMessages);
        messButBox.setAlignment(Pos.CENTER_RIGHT);

        // add the buttons
        messPane.add(messButBox, 2, 4);
        
        // add the send text area
        messPane.add( tfMessage, 0, 5, 3, 1);
        
        // set the button sizes
        btnClearReceivedMessages.setPrefWidth(80);
        btnSendMessage.setPrefWidth(80);
        btnClearSendMessages.setPrefWidth(80);
        // set the list view width
        messageListView.setPrefWidth(300);
        // set the text area width
        tfMessage.setPrefWidth(300);

        // right align the message clear button
        GridPane.setHalignment( btnClearReceivedMessages, HPos.RIGHT);
        
        // add the message controls to the main grid
        layoutGrid.add( messPane, 1, 0, 1, 2);
        
    }
    
    // add the close button and message text field
    private static void addCloseToGui(GridPane layoutGrid)
    {
        // add text to the grid
        layoutGrid.add( messLabel, 0, 2);

        // add the close button
        layoutGrid.add(btnClose, 1, 2);
        // set the button size
        btnClose.setPrefWidth(80);
        
        // right align the close button
        GridPane.setHalignment( btnClose, HPos.RIGHT);
        
    }
    
    // set the event listener code
    private static void addEventListeners()
    {
        // add the event listeners for each button

        // start server button
        btnStartServer.setOnAction((ActionEvent event) -> {
            StartServer();});

        // log in button
        btnLogIn.setOnAction((ActionEvent event) -> {
            UserLogIn();});

        // log out button
        btnLogOut.setOnAction((ActionEvent event) -> {
            UserLogOut();});

        // disconnect user button
        btnDisconnect.setOnAction((ActionEvent event) -> {
            DisconnectUser();});

        // new user button
        btnNewUser.setOnAction((ActionEvent event) -> {
            NewUser();});

        // clear received messages button
        btnClearReceivedMessages.setOnAction((ActionEvent event) -> {
            ClearMessageReceived();});

        // clear sent message button
        btnClearSendMessages.setOnAction((ActionEvent event) -> {
            ClearMessageSent();});

        // send message button
        btnSendMessage.setOnAction((ActionEvent event) -> {
            SendMessage();});

        // close application button
        btnClose.setOnAction((ActionEvent event) -> {
            CloseApplication();});
        
        // a record is selected from the list view
        clientListView.setOnMouseClicked((MouseEvent event) -> {
            TestSelection();});
        
    }
    
    // button methods
    // start the server
    private static void StartServer()
    {
        if( MessageServer.testingFlag)
            System.out.println("start server");
        
        // start the server
        // MessageServer.StartSocketServer();
        
    }
    
    // user login 
    private static void UserLogIn()
    {
        if( MessageServer.testingFlag)
            System.out.println("user log in");
        
        // call the login method
        MessageServer.UserLogIn();
        
    }
    
    // user logout 
    private static void UserLogOut()
    {
        if( MessageServer.testingFlag)
            System.out.println("user log out");
        
        // call the logout method
        MessageServer.UserLogOut();
        
    }
    
    // disconnect user
    private static void DisconnectUser()
    {
        if( MessageServer.testingFlag)
            System.out.println("disconnect user");
        
        // MessageServer.DisconnectSelectedUser();
        
    }
    
    // make a new user
    private static void NewUser()
    {
        if( MessageServer.testingFlag)
            System.out.println("new user");
        
        // call the create user method
        MessageServer.CreateNewUser();
        
    }
    
    // clear message received list
    public static void ClearMessageReceived()
    {
        if( MessageServer.testingFlag)
            System.out.println("clear message received");
        
        // clear the list of messages
        messageList.clear();
        
    }
    
    // clear message sent box
    public static void ClearMessageSent()
    {
        if( MessageServer.testingFlag)
            System.out.println("clear message sent");
        
        // clear the text area
        tfMessage.clear();
        
    }
    
    // send a message
    private static void SendMessage()
    {
        if( MessageServer.testingFlag)
            System.out.println("send message");
        // broadcast the message
        // MessageServer.BroadcastMessage();
        
    }
    
    // close the aplication
    private static void CloseApplication()
    {
        if( MessageServer.testingFlag)
            System.out.println("close application");
        // close the application
        System.exit(0);
        
    }
    
    // test the record selected by the mouse
    private static void TestSelection()
    {
        // declare local variables
        String clientRecord;
        
        if( MessageServer.testingFlag)
            System.out.println("client selected");
        
        // get the current record
        clientRecord = GetSelectedClient();
        
        // test for the active flag
        // and not the admin account
        if( clientRecord.contains("true") &&
                !clientRecord.contains(MessageServer.ADMIN_USER))
            // enable the disconnect button
            btnDisconnect.setDisable(false);
        else
            // disable the disconnect button
            btnDisconnect.setDisable(true);
        
    }
    
    // get the currently selected record
    public static String GetSelectedClient()
    {
        return clientListView.getSelectionModel().getSelectedItem();
        
    }
    
    // show an error message
    public static void ShowErrorMessage( String message)
    {
        // set the message
        messLabel.setText(message);
        
    }
    
    // read a value from the text fields
    // get the value from the user name field
    public static String GetUserName() throws Exception
    {
        // declare local variables
        String text;
        // read the text field
        text = tfUserName.getText();
        
        if( text.isBlank())
            throw new Exception("please enter a user name");
        else
            return text;
        
    }

    // get the value from the user password field
    public static String GetUserPw() throws Exception
    {
        // declare local variables
        String text;
        // read the text field
        text = tfPassword.getText();
        
        if( text.isBlank())
            throw new Exception("please enter a user password");
        else
            return text;
        
    }
    
    // get the message to send
    public static String GetSendMessage() throws Exception
    {
        // declare local variables
        String text;
        // read the text field
        text = tfMessage.getText();
        
        if( text.isBlank())
            throw new Exception("please enter message to send");
        else
            return text;
        
    }
    
    // clear the user inputs
    public static void ClearUserFields()
    {
        // clear the fields
        tfUserName.clear();
        tfPassword.clear();
        
    }
    
    // interfaces changes for an admin login
    public static void AdminLogin()
    {
        // enable the button
        btnNewUser.setDisable(false);
        
    }
    
    // interfaces changes for an admin log out
    public static void AdminLogout()
    {
        // enable the button
        btnNewUser.setDisable(true);
        
    }

    // show a received message
    public static void ShowReceivedMessage( String message)
    {
        messageList.add(message);
        
    }
    
    // set the socket server status flag
    public static void SetSocketStatus( String message)
    {
        tfPipeName.setText(message);
    
    }
    
    // disable the server start button
    public static void DisableServerStartButton()
    {
        btnStartServer.setDisable(true);
        
    }
    
    // enable the server start button
    public static void EnableServerStartButton()
    {
        btnStartServer.setDisable(false);
        
    }
    
    
}
