/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import java.io.DataOutputStream;
import javafx.collections.ObservableList;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 09/05/2020
 * Java III
 * Portfolio AT2 Question 4
 * 
 * inter process communication
 * server application 
 * code to manage the client list
 * 
 */

public class ClientList {
    // declare class variables
    // declare the root node for the binary tree
    private Client rootNode;
    
    // a reference to the client display list
    private ObservableList<String> clientList;
    
    // constructor code
    public ClientList(ObservableList<String> newList)
    {
        // set the reference to the client display list
        clientList = newList;
        // set the root node to null
        rootNode = null;
        
    }
    
    // create a client object from a name and password and add to the list
    public void AddClient( String name, String newPw) throws Exception 
    { 
        // declare local variables
        Client newClient;

        // test for an existing client
        if( FindNode(name) != null)
            throw new Exception("duplicate client name");
        // test for an invalid password
        if( name.equalsIgnoreCase(newPw))
            throw new Exception("password and name must be different");

        // generate the salt code for this client
        String newSalt = PassWordManager.GetSalt();
        
        // hash the password
        String newHashPw = PassWordManager.GetHashedPassword(newPw, newSalt);
        
        // create the new client object
        newClient = new Client( name, newHashPw, newSalt, false);
        
        // create and add the new client
        AddNewNode(newClient);
        
        // add the new user to the database
        DataStore.NewUser(newClient);
    
    }
    
    // create a client object from a database record and add to the list
    public void LoadClient( String dbName, String dbSalt, String dbHash) throws Exception 
    { 
        // declare local variables
        Client newClient;
        String logMessage;
    
        // test for an existing client
        if( FindNode(dbName) != null)
            throw new Exception("duplicate client name");
        
        // create the new client object
        newClient = new Client( dbName, dbHash, dbSalt, false);
        
        // create and add the client
        AddNewNode(newClient);
    
        // create the log report
        logMessage = String.format("user record read for %s", dbName);

        // display message in testing mode
        if( MessageServer.testingFlag)
            MainForm.ShowReceivedMessage(MessageServer.SYS_USER + logMessage);
        
    }

    // add a new node to the tree
    public void AddNewNode( Client newNode)
    {
        // add the node into the tree
        rootNode = AddNodes( rootNode, newNode);
        
        // update the list of clients
        ListNodes();
        
    }
            
    // recursively search the tree to find the 
    // location to add the new node to
    private Client AddNodes( Client thisNode, Client newNode)
    {
        // declare local variables
        // string comparison factor
        int compareValue = 0;
        // branch balance factor
        int balanceFactor = 0;
        
        // test for a null node
        if( thisNode == null)
            // end of the tree add the new node here
            return newNode;
    
        // test the new part against the current part
        compareValue = newNode.getName().compareTo(thisNode.getName());
        
        // add the node to the appropriate child link
        if( compareValue < 0)
        {
            // new value is smaller
            // search the left tree
            thisNode.setLeftNode( AddNodes(thisNode.getLeftNode(), newNode));
        }
        else if ( compareValue > 0)
        {
            // new value is larger
            // search the right tree
            thisNode.setRightNode( AddNodes(thisNode.getRightNode(), newNode));
        }
        else
            // the client already exists
            // don't add duplicate values
            return thisNode;
    
        // calculate the balance factor
        balanceFactor = CalcBalanceFactor(thisNode);
        
        // test the balance of the tree
        if( balanceFactor < -1)
        {
            // the tree is right heavy
            // test where the node was added
            if( newNode.getName().compareTo(thisNode.getRightNode().getName()) < 0)
            {
                // apply right/left rotation
                thisNode = RightLeftRotation( thisNode);
            }
            else
            {
                // apply left rotation
                thisNode = LeftRotation( thisNode);
            }
        }
        else if ( balanceFactor > 1)
        {
            // the tree is left heavy
            // test where the node was added
            if( newNode.getName().compareTo(thisNode.getLeftNode().getName()) < 0)
            {
                // apply right rotation
                thisNode = RightRotation( thisNode);
            }
            else
            {
                // apply left/right rotation
                thisNode = LeftRightRotation( thisNode);
            }
        }
        
        // update the height value
        thisNode.setHeight(FindHeight(thisNode));
        
        // return the final node
        return thisNode;
    
    }
    
    // find the height of a tree
    private int FindHeight( Client thisNode)
    {
        // declare local varibles
        int leftHeight = 0;
        int rightHeight = 0;
        
        // get left node height
        if(thisNode.getLeftNode() != null)
            leftHeight = thisNode.getLeftNode().getHeight();
        
        // get right node height
        if(thisNode.getRightNode() != null)
            rightHeight = thisNode.getRightNode().getHeight();
        
        // check the height of the left tree and the right tree
        // return the larger value + 1 for this node
        return Math.max( leftHeight, rightHeight) + 1;
        
    }
    
    // calculate a balance factor
    private int CalcBalanceFactor( Client thisNode)
    {
        // declare local varibles
        int leftHeight = 0;
        int rightHeight = 0;
        
        // get left node height
        if(thisNode.getLeftNode() != null)
            leftHeight = thisNode.getLeftNode().getHeight();
        
        // get right node height
        if(thisNode.getRightNode() != null)
            rightHeight = thisNode.getRightNode().getHeight();
        
        // subtract the right height from the left height
        // to calcuate the balance factor
        return leftHeight - rightHeight;
        
    }
    
    // apply a right rotation to the tree
    private Client RightRotation( Client node2)
    {
        // create a new node from the left tree
        Client node1 = node2.getLeftNode();
        // shift the right tree from the new node
        node2.setLeftNode( node1.getRightNode());
        // add the target to the right side of the new node
        node1.setRightNode( node2);
        // update the height values
        node1.setHeight(FindHeight(node1));
        node2.setHeight(FindHeight(node2));

        // return the new node
        return node1;
        
    }
    
    // apply a left rotation to the tree
    private Client LeftRotation( Client node1)
    {
        // create a new node from the right tree
        Client node2 = node1.getRightNode();
        // copy the left tree from the new node
        node1.setRightNode( node2.getLeftNode());
        // add the target to the left side of the new node
        node2.setLeftNode( node1);
        // update height values
        node1.setHeight(FindHeight(node1));
        node2.setHeight(FindHeight(node2));

        // return new node
        return node2;
        
    }
    
    // apply a left/right rotation
    private Client LeftRightRotation( Client node3)
    {
        // apply the left rotation
        node3.setLeftNode(LeftRotation(node3.getLeftNode()));
        // apply the right rotation
        return RightRotation( node3);
        
    }
    
    // apply a right/left rotation
    private Client RightLeftRotation( Client node3)
    {
        // apply the right rotation
        node3.setRightNode(RightRotation(node3.getRightNode()));
        // apply the left rotation
        return LeftRotation( node3);
        
    }
    
    // search for a value in the tree
    public Client FindNode( String searchValue)
    {
        return SearchNodes( rootNode, searchValue);
    }
    
    // recursivly search down the tree for the specified value
    // return the node if one is found
    private Client SearchNodes( Client thisNode, String searchValue)
    {
        // declare local variables
        int compareValue;
        
        // test for a null node
        if( thisNode == null)
            // end of the tree the value was not found
            // return a null value
            return null;

        // test the new part against the current part
        compareValue = searchValue.compareTo(thisNode.getName());

        // check the compare value result
        if( compareValue == 0)
            // the value was found
            return thisNode;
        else if( compareValue < 0)
            // search down the left branch
            return SearchNodes( thisNode.getLeftNode(), searchValue);
        else
            // search right node
            return SearchNodes( thisNode.getRightNode(), searchValue);
        
    }
    
    // make a list of all nodes in the tree
    public void ListNodes( )
    {
        // remove any existing nodes from the list
        clientList.clear();
        // search the tree adding nodes to the list
        ListNodesAdd( rootNode);
    }
    
    // recursively search the tree adding each node to the list
    // in the sorted order
    private void ListNodesAdd( Client thisNode)
    {
        // if the node is not null
        // try to search the next level
        if( thisNode != null)
        {
            // test the left child
            ListNodesAdd( thisNode.getLeftNode());
            // add the current node
            clientList.add(thisNode.toString());
            // test the right child
            ListNodesAdd( thisNode.getRightNode());
        }
        
    }
    
    // delete a node from the list
    public void Delete( String searchValue)
    {
        rootNode = DeleteNode( rootNode, searchValue);
        
    }
    
    // recursively search the tree
    // if the node is found remove it
    private Client DeleteNode( Client thisNode, String searchValue)
    {
        // declare variables
        // string compareto value search string vs current node
        int compareValue;
        // place holder for the 2 child node case
        Client smallestNode;
        // tree balance factor
        int balanceFactor;
        
        // test for a null branch
        if( thisNode == null)
            return null;

        // test the new part against the current part
        compareValue = searchValue.compareTo(thisNode.getName());
        
        // compare the string values
        if( compareValue == 0)
        {
            // test for no children
            // if no children found simply remove this node
            if( thisNode.getLeftNode() == null && 
                    thisNode.getRightNode() == null)
                return null;
            
            // test for one child
            // test for a left child only
            if( thisNode.getRightNode() == null)
                // left child only return the left child
                return thisNode.getLeftNode();

            // test for a right child only
            if( thisNode.getLeftNode() == null)
                // left child only return the left child
                return thisNode.getRightNode();
            
            // test for 2 children
            // find the smallest value down the right branch
            smallestNode = FindSmallestNode(thisNode.getRightNode());
            // remove the reference to the smallest node
            thisNode.setRightNode(DeleteNode(thisNode.getRightNode(),
                                  smallestNode.getName()));
            // adjust the links in the tree
            // so that the smallest node replaces the target node
            smallestNode.setLeftNode(thisNode.getLeftNode());
            smallestNode.setRightNode(thisNode.getRightNode());
            // update the height value
            smallestNode.setHeight(FindHeight(smallestNode));

            return smallestNode;
            
        }
        else 
        {    
            if( compareValue < 0)
            {
                // continue the search down the left branch
                thisNode.setLeftNode( DeleteNode( thisNode.getLeftNode(), 
                                      searchValue));

            }
            else
            {
                // continue the search down the right branch
                thisNode.setRightNode( DeleteNode( thisNode.getRightNode(), 
                                       searchValue));

            }
    
            // calculate the balance factor
            balanceFactor = CalcBalanceFactor(thisNode);

            // test the balance of the tree
            if( balanceFactor < -1)
            {
                // the tree is right heavy
                // test where the node was deleted
                if( searchValue.compareTo(thisNode.getRightNode().getName()) > 0)
                {
                    // apply right/left rotation
                    thisNode = RightLeftRotation( thisNode);
                }
                else
                {
                    // apply left rotation
                    thisNode = LeftRotation( thisNode);
                }
            }
            else if ( balanceFactor > 1)
            {
                // the tree is left heavy
                // test where the node was deleted
                if( searchValue.compareTo(thisNode.getLeftNode().getName()) > 0)
                {
                    // apply right rotation
                    thisNode = RightRotation( thisNode);
                }
                else
                {
                    // apply left/right rotation
                    thisNode = LeftRightRotation( thisNode);
                }
            }

            // update the height value
            thisNode.setHeight(FindHeight(thisNode));
            return thisNode;

        }

    }
    
    // search the tree for the smallest node
    private Client FindSmallestNode( Client testNode)
    {
        // test the left node for a link
        if( testNode.getLeftNode() == null)
            return testNode;
        else
            return FindSmallestNode(testNode.getLeftNode());
            
    }

    
    // broadcast a message to all clients
    public void MessageAllClients( String message)
    {
        // loop through the list of clients
        // only message active clients
        // do not message the admin account
        FindActiveClients(rootNode, message);
        
    }
    
    // recursively search the tree 
    // search for active clients
    // message the active clients
    private void FindActiveClients( Client thisNode, String message)
    {
        // if the node is not null
        // try to search the next level
        if( thisNode != null)
        {
            // test the left child
            FindActiveClients( thisNode.getLeftNode(), message);
            // check the current node
            if( thisNode.getActiveFlag() && 
                    !MessageServer.ADMIN_USER.equals(thisNode.getName()))
                MessageOneClient(thisNode, message);
            
            // test the right child
            FindActiveClients( thisNode.getRightNode(), message);
            
        }
        
    }
    
    // send a message to one client
    public void MessageOneClient( Client cl, String message)
    {
        // try to send the message
        try
        {
            // get the output stream
            DataOutputStream writer = cl.getWriteStream();
            // write the message
            writer.writeUTF(message);
            
        }
        catch ( Exception ex)
        {
            MainForm.ShowErrorMessage(ex.getMessage());
            
        }
        
    }
    
    // get the client name from a client list record
    public String GetClientNameFromListRecord( String clientRecord)
    {
        // declare local variables
        int nameEnd;
        
        // search for the start of the active flag
        nameEnd = clientRecord.indexOf("active");
        // adjust for the trailing space
        nameEnd--;
        
        // return the substring
        return clientRecord.substring(6, nameEnd);
        
    }
    
}
