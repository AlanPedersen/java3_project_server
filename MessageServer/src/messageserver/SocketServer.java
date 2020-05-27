/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 26/05/2020
 * Java III
 * Project AT3 Question 3
 * 
 * inter process communication
 * server application 
 * code to manage one client connection
 * 
 */

public class SocketServer extends Thread {
    
    // declare socket and stream variables
    protected Socket ServerSocket;
    protected DataInputStream readStream;
    protected DataOutputStream writeStream;
    
    // user account variables
    ClientList ClientManager;
    private String userName;
    private String userPw;
    private Client testClient;
    private Boolean validPassword;
    
    // message returned to user
    private String message;
    // message read from stream
    private String readMessage;

    public SocketServer(Socket newSocket, ClientList newClientList)
    {
        // set the socket object
        ServerSocket = newSocket;
        // set the client manager object
        ClientManager = newClientList;
        
        // progress message
        if( MessageServer.testingFlag)
            System.out.println("New client connected from " 
                    + newSocket.getInetAddress().getHostAddress());
        
        // start the thread
        start();
        
    }

    public void run() {

        try {
            // open the streams
            readStream = new DataInputStream( ServerSocket.getInputStream());
            writeStream = new DataOutputStream( ServerSocket.getOutputStream());
            
            // read the user name & password
            userName = readStream.readUTF();
            userPw = readStream.readUTF();
            
            // echo the values if we are in testing mode
            if( MessageServer.testingFlag)
            {
                System.out.printf("user name: %s\n", userName);
                System.out.printf("user pw: %s\n", userPw);
                
            }
            
            // test the submitted user name and password
            // get the client object
            testClient = ClientManager.FindNode(userName);
            
            // test for a null client
            if( testClient == null)
            {
                // write a fail message
                writeStream.writeUTF(MessageServer.LOGIN_FAIL);
                // write user message
                message = String.format("invalid user ID %s", userName);
                writeStream.writeUTF(message);
                
                // local message echo
                message = String.format("attempted login from invalid user %s", userName);
                MainForm.ShowReceivedMessage(message);
                // record log event
                DataStore.AddLogRecord(message);

                if( MessageServer.testingFlag)
                    System.out.println(message);
                
            }
            else
            {
                // test the password
                validPassword = PassWordManager.CheckPassword( userPw, 
                        testClient.getPasswordHash(),
                        testClient.getPasswordSalt());
                
                // set the active flag
                testClient.setActiveFlag(validPassword);

                // update the client list
                ClientManager.ListNodes();

                // was the username & password combination valid
                if( validPassword)
                {
                    // write a login pass message
                    writeStream.writeUTF(MessageServer.LOGIN_PASS);
                    // write user message
                    writeStream.writeUTF("logged in to server");
                
                    // local message echo
                    message = String.format("sucessfull login from %s", userName);
                    MainForm.ShowReceivedMessage(message);
                    
                    // record log event
                    DataStore.AddLogRecord(message);
                    
                    if( MessageServer.testingFlag)
                        System.out.println(message);

                    // set the socket controls in the client object
                    testClient.setServerSocket(ServerSocket);
                    testClient.setReadStream(readStream);
                    testClient.setWriteStream(writeStream);
                    
                    // run a loop listening for messages
                    while(true)
                    {
                        // read a message 
                        readMessage = readStream.readUTF();
                        
                        // test for a close signal
                        if( MessageServer.DISCONNECT.equals(readMessage))
                        {
                            // local log out message
                            message = String.format("log out by %s", userName);
                            MainForm.ShowReceivedMessage(message);

                            // record log event
                            DataStore.AddLogRecord(message);

                            // exit loop
                            break;
                            
                        }
                        
                        // prepend the user id to the message
                        message = userName + ": " + readMessage;
                        
                        // display the message
                        MainForm.ShowReceivedMessage(message);
                        
                    }
                    
                }
                else
                {
                    // error message invalid password fail

                    // write a fail message
                    writeStream.writeUTF(MessageServer.LOGIN_FAIL);
                    // write user message
                    message = String.format("the password is not valid for %s", 
                            userName);
                    writeStream.writeUTF(message);
                
                    // local message echo
                    message = String.format("failed login from %s", userName);
                    MainForm.ShowReceivedMessage(message);

                    // record log event
                    DataStore.AddLogRecord(message);

                    if( MessageServer.testingFlag)
                        System.out.println(message);
                    
                }
                
            }
            
        } 
        catch (IOException ex) 
        {
            // display the error message
            MainForm.ShowErrorMessage(ex.getMessage());
        
        } 
        finally 
        {
            try 
            {
                // reset active status for client
                testClient.setActiveFlag(false);
                // update the client list
                ClientManager.ListNodes();

                readStream.close();
                writeStream.close();
                
                // close the server socket if equired
                if( !ServerSocket.isClosed())
                    ServerSocket.close();
                
            } 
            catch (IOException ex) 
            {
                // ex.printStackTrace();
                MainForm.ShowErrorMessage(ex.getMessage());
                
            }
        }
        
    }
    
}
