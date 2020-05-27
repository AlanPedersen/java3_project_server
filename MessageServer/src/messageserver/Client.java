/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 26/05/2020
 * Java III
 * Project AT3 Question 3
 * 
 * inter process communication
 * server application 
 * code to manage one client
 * 
 */

public class Client {
    // declare client attributes
    // login name
    private String name;
    // password controls
    private String passwordHash;
    private String passwordSalt;
    // active flag
    private boolean activeFlag;
    // socket controls
    private Socket ServerSocket;
    private DataInputStream readStream;
    private DataOutputStream writeStream;
    // left and right tree branches
    private Client left;
    private Client right;
    // the tree height at this node
    private int height;


    // constructor code
    public Client(String name, 
            String passwordHash, String passwordSalt, 
            boolean activeFlag) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.activeFlag = activeFlag;
        
        // default settings for a new node
        this.left = null;
        this.right = null;
        this.height = 1;
        
    }
    
    // getter and setter code ( auto generated)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    public Socket getServerSocket() {
        return ServerSocket;
    }

    public void setServerSocket(Socket ServerSocket) {
        this.ServerSocket = ServerSocket;
    }

    public DataInputStream getReadStream() {
        return readStream;
    }

    public void setReadStream(DataInputStream readStream) {
        this.readStream = readStream;
    }

    public DataOutputStream getWriteStream() {
        return writeStream;
    }

    public void setWriteStream(DataOutputStream writeStream) {
        this.writeStream = writeStream;
    }

    public Client getLeftNode() {
        return left;
    }
    
    public void setLeftNode( Client newNode) {
        this.left = newNode;
    }

    public Client getRightNode() {
        return right;
    }
    
    public void setRightNode( Client newNode) {
        this.right = newNode;
    }

    // get the node height
    public int getHeight() {
        return height;
    }

    // set the tree height
    public void setHeight(int newHeight) {
        height = newHeight;
    }
    
    // override the ToString method
    @Override
    public String toString() {
        return "name: " + name + " active: " + activeFlag;
    }
    
}
