package main;

import main.UserManager;
import main.Auction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Vector;

public class UserManagerTest {
    /**
     * Test of validateDouble method, of class UserManager.
     * Testing if the validate double function is able to convert string to double.
     */
    @Test
    public void testValidateDouble() {
        System.out.println("UserManagerTest/testValidateDouble()...");
        String doubleStr = "0.0";
        UserManager instance = new UserManager();
        double expResult = 0.0;
        double result = instance.validateDouble(doubleStr);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of load method, of class UserManager.
     * Testing the file reader for current_user_accounts.txt.
     */
    @Test
    public void testLoad() {
        System.out.println("UserManagerTest/testLoad()...");
        String filename = "Backend/iofiles/current_users_accounts.txt";
        UserManager instance = new UserManager();
        instance.load(filename);
    }

    // ---- PATH COVERAGE TESTING FOR: create ----

    /**
     * Test of createUser method, of class UserManager.
     * Testing if the create user function works for unique usernames for the UserManager class.
     */
    @Test
    public void testCreateUser() {
        System.out.println("UserManagerTest/testCreateUser()...");
        String transaction = "01 user07 SS 0 teww";
        UserManager instance = new UserManager();
        instance.createUser(transaction);
        instance.load("Backend/iofiles/current_users_accounts.txt");
        int initialUserCount = instance.getUsers().size();
        instance.createUser(transaction);
        int updatedUserCount = instance.getUsers().size();
        assertEquals(initialUserCount, updatedUserCount);
    }

    /*
     * Test of createUser method, of class UserManager.
     * Testing if the error handling works for the create user function for duplicate usernames for the UserManager class.
     */
    @Test
    public void testCreateUserError() {
        System.out.println("UserManagerTest/testCreateUserError()...");
        String transaction = "01 user01 SS 0 teww";
        String username = transaction.substring(3, 9);
        UserManager instance = new UserManager();
        Vector<User> users = instance.getUsers();
        boolean foundUser = false;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                foundUser = true;
                break;
            }
        }
        assertFalse(foundUser);
        instance.createUser(transaction);
    }

    /**
     * Test of deleteUser method, of class UserManager.
     * Testing if the delete user function works for UserManagerclass.
     */
    @Test
    public void testDeleteUser() throws IOException {
        System.out.println("UserManagerTest/testDeleteUser()...");
        UserManager instance = new UserManager();
        //instance.load("Backend/iofiles/current_users_accounts.txt");
        Auction auctions = new Auction();
        String createTransaction = "01 ss01 SS 0 teww";
        instance.createUser(createTransaction);
        String deleteTransaction = "02 ss01 SS 0 teww";
        int initialSize = instance.getUsers().size();
        instance.deleteUser(deleteTransaction, auctions);
        int finalSize = instance.getUsers().size();
        assertEquals(initialSize - 1, finalSize);
    }

    /**
     * Test of refund method, of class UserManager.
     * Testing if the refund function works for the UserManager class.
     */
    @Test
    public void testRefund() {
        System.out.println("UserManagerTest/testRefund()...");
        String transaction = "05 user01 user02 50";
        UserManager instance = new UserManager();
        instance.load("Backend/iofiles/current_users_accounts.txt");

        // Assert that the refund method does not throw an exception
        try {
            instance.refund(transaction);
        } catch (Exception e) {
            fail("Refund method threw an exception: " + e.getMessage());
        }

        // Assert that the user's balance has been updated correctly
        User user1 = null;
        User user2 = null;
        Vector<User> users = instance.getUsers();
        for (User user : users) {
            if (user.getUsername().equals("user01")) {
                user1 = user;
            } else if (user.getUsername().equals("user02")) {
                user2 = user;
            }
        }

        double expectedBalance1 = 200.0;
        double expectedBalance2 = 0.0;
        assert user1 != null;
        assertEquals(expectedBalance1, user1.getCreditAmount(), 0.0);
        assert user2 != null;
        assertEquals(expectedBalance2, user2.getCreditAmount(), 0.0);
    }


    /**
     * Test of addCredit method, of class UserManager.
     * Testing if the add credit function works for the UserManager class.
     */
    @Test
    public void testAddCredit() {
        System.out.println("UserManagerTest/testAddCredit()...");
        String transaction = "06 user01 AA 50";
        UserManager instance = new UserManager();
        instance.load("Backend/iofiles/current_users_accounts.txt");
        instance.addCredit(transaction);
        User user1 = null;
        Vector<User> users = instance.getUsers();
        for (User user : users) {
            if (user.getUsername().equals("user01")) {
                user1 = user;
            }
        }
        assert user1 != null;
        assertEquals(200.0, user1.getCreditAmount(), 0.0); // assuming user had 5.00 credit before adding 5.00
    }


    /**
     * Test of closeAuction method, of class UserManager
     * Testing if the output to the current_user_accounts.txt is correct for UserManager class.
     */
    @Test
    public void testCloseAuction() {
        System.out.println("UserManagerTest/testCloseAuction()...");
        UserManager instance = new UserManager();
        instance.load("Backend/iofiles/current_users_accounts.txt");
        User buyerUser = null;
        User sellerUser = null;
        double amount = 50;
        Vector<User> users = instance.getUsers();
        for (User user : users) {
            if (user.getUsername().equals("user03")) {
                buyerUser = user;
            } else if (user.getUsername().equals("user04")) {
                sellerUser = user;
            }
        }
        assert buyerUser != null;
        assert sellerUser != null;
        instance.closeAuction(buyerUser.getUsername(), sellerUser.getUsername(), amount);
        assertEquals(40.0, buyerUser.getCreditAmount(), 0.0); // assuming buyer had 10.00 credit before the transaction
        assertEquals(225.0, sellerUser.getCreditAmount(), 0.0); // assuming seller had 20.00 credit before the transaction
    }

    /**
     * Test of write method, of class Users.
     */
    @Test
    public void testWrite() throws Exception {
        System.out.println("UserManagerTest/testWrite()...");
        String filename = "user_testLog.txt";
        UserManager instance = new UserManager();
        instance.load("Backend/iofiles/current_users_accounts.txt");
        instance.write(filename);
    }
}
