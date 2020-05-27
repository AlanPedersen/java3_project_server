package messageserver;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 26/05/2020
 * Java III
 * Project AT3 Question 3
 * 
 * inter process communication
 * server application 
 * thread to listen for client connections
 * 
 */

public class ListenForClient extends Thread {
    // declare variables
    private static int ServerPortNumber;
    // reference to the client list
    private static ClientList ClientManager;
    
    // constructor code
    public ListenForClient( ClientList newClientList, int serverPort)
    {
        // set variables
        ServerPortNumber = serverPort;
        ClientManager = newClientList;
        
    }

    public void run() {
        if( MessageServer.testingFlag)
            System.out.println("SocketServer Started");
        
        // update the server status flag
        String message = String.format("running on port %d", ServerPortNumber);
        MainForm.SetSocketStatus(message);
        // record log event
        DataStore.AddLogRecord(message);
        
        // disable the server start button
        MainForm.DisableServerStartButton();
        
        ServerSocket server = null;
        
        try 
        {
            server = new ServerSocket(ServerPortNumber);
            while (true) 
            {
                new SocketServer(server.accept(), ClientManager);
                
            }
            
        } 
        catch (IOException ex) 
        {
            MainForm.ShowErrorMessage("Unable to start server.");
            
        } 
        finally 
        {
            try 
            {
                if (server != null)
                    server.close();
                // enable the server start button
                MainForm.EnableServerStartButton();
                // update the server status
                MainForm.SetSocketStatus("not running");
                
            } 
            catch (IOException ex) 
            {
                // ex.printStackTrace();
                MainForm.ShowErrorMessage(ex.getMessage());
            }
        }
    }
    
}
