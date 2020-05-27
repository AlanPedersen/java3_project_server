/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Name: Alan Pedersen
 * ID: P225139
 * Date: 09/05/2020
 * Java III
 * Portfolio AT2 Question 4
 * 
 * inter process communication
 * server application 
 * code to managing passwords
 * 
 */

public class PassWordManager {

    // create a password salt value
    public static String GetSalt() throws NoSuchAlgorithmException
    {
        // generate a secure random number
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        String stringSalt = new String(salt);
        return stringSalt;
        
    }

    // generate a hashed salted password
    public static String GetHashedPassword(String passwordToHash, String salt)
    {
        // initialise the hashed password
        String generatedPassword = null;
        try {
            // instantiate the digester
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // add the salt value to the hash function
            md.update(salt.getBytes());
            // add the password to the hash function
            // return the hashed salted password
            byte[] bytes = md.digest(passwordToHash.getBytes());
            // convert the btye array to a string
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // get the string value
            generatedPassword = sb.toString();
            
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
            
        }
        
        // return the salted hashed password
        return generatedPassword;
        
    }
    
    // test a password against the stored hash
    public static Boolean CheckPassword( String checkWord,
            String hashCode, String salt)
    {
        // hash the password to check
        String checkHash = GetHashedPassword(checkWord, salt);
        // test against the stored hash value
        return hashCode.equals(checkHash);
        
    }
    
}
