package main;

import main.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    /**
     * Test of getUsername method, of class User.
     * Testing the getter for a user's username in the User class.
     */
    @Test
    public void testGetUsername() {
        System.out.println("Testing UserTest/testGetUsername()...");
        User instance = new User("admin01", "AA", 0.0, "pass");
        String expResult = "admin01";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getAccountType method, of class User.
     * Testing the getter for a user's account type in the User class.
     */
    @Test
    public void testGetAccountType() {
        System.out.println("Testing UserTest/testGetAccountType()...");
        User instance = new User("admin01", "AA", 0.0, "pass");
        String expResult = "AA";
        String result = instance.getAccountType();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getCreditAmount method, of class User.
     * Testing the getter for a user's credit amount in the User class.
     */
    @Test
    public void testGetCreditAmount() {
        System.out.println("Testing UserTest/testGetCreditAmount()...");
        User instance = new User("admin01", "AA", 100.00, "pass");
        double expResult = 100.00;
        double result = instance.getCreditAmount();
        assertEquals(expResult, result, 0.0);
    }
    
    /**
     * Test of setUsername method, of class User.
     * Testing the setter for a user's username in the User class.
     */
    @Test
    public void testSetUsername() {
        System.out.println("Testing UserTest/testSetUsername()...");
        String username = "newUserName";
        User instance = new User("originalUsername", "AA", 0.0, "pass");
        instance.setUsername(username);

        String result = instance.getUsername();
        assertEquals(username, result);
    }
    
    
    /**
     * Test of setAccountType method, of class User.
     * Testing the setter for a user's account type in the User class.
     */
    @Test
    public void testSetAccountType() {
        System.out.println("Testing UserTest/testSetAccountType()...");
        String accountType = "AA";
        User instance = new User("testUser", "FS", 0.0, "pass"); // Change from FS to AA
        instance.setAccountType(accountType);

        String result = instance.getAccountType();
        assertEquals(accountType, result);
    }
    
    
    /**
     * Test of setCreditAmount method, of class User.
     * Testing the setter for a user's credit amount in the User class.
     */
    @Test
    public void testSetCreditAmount() {
        System.out.println("Testing UserTest/testSetCreditAmount()...");
        double creditAmount = 100.00;
        User instance = new User("admin01", "AA", 0.0, "pass"); // Change from 0.0 credit to 100.00 credit
        instance.setCreditAmount(creditAmount);

        double result = instance.getCreditAmount();
        assertEquals(creditAmount, result, 0.0);
    }


    /**
     * Test of updateCreditAmount method, of class User.
     * Testing update credit for a user's credit amount in the User class.
     */
    @Test
    public void testUpdateCreditAmount() {
        System.out.println("Testing UserTest/testUpdateCreditAmount()...");
        
        // TEST - INCREASE CREDIT AMOUNT \\
        double credit = 100.00;
        User instance = new User("admin01", "AA", 50.00, "pass");
        instance.updateCreditAmount(credit);
        
        double expResult = 150.00;
        double result = instance.getCreditAmount();
        assertEquals(expResult, result, 0.0);
        
        // TEST - DECREASE CREDIT AMOUNT \\
        credit = -100.00;
        instance.updateCreditAmount(credit);
        expResult = 50.00;
        result = instance.getCreditAmount();
        assertEquals(expResult, result, 0.0); 
    }

    /**
     * Test of formatOutput method, of class User.
     * Testing the output format for User class.
     */
    @Test
    public void testFormatOutput() {
        System.out.println("Testing UserTest/testFormatOutput()...");
        User instance = new User("user05", "AA", 0.00, "teww");
        String expResult = "user05 AA 0.00 teww";
        String result = instance.formatOutput();
        assertEquals(expResult, result);
    }
    
}
