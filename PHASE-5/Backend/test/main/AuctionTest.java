package main;

import main.UserManager;
import main.Auction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Vector;

public class AuctionTest {
    /**
     * Test of load method, of class Auction.
     * Testing the file reader for available_items.txt.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("Testing AuctionTest/testLoad()...");
        String filename = "Backend/iofiles/available_items.txt";
        Auction instance = new Auction();
        instance.load(filename);
    }

    /**
     * Test of advertise method, of class Auction.
     * Testing the advertise function to post a new item in the auction for Auction class.
     */
    @Test
    public void testAdvertise() {
        System.out.println("Testing AuctionTest/testAdvertise()...");
        // Create instance of UserManager and create user03
        UserManager umInstance = new UserManager();
        String createTransaction = "01 user03 BS 0 teww";
        umInstance.createUser(createTransaction);

        // Create an instance of the Auction class and call the method
        Auction instance = new Auction();
        // Define the input and expected output of the method
        String adTransaction = "03 computer user03 NA 50 100.0";
        boolean expectedOutput = true;
        instance.advertise(adTransaction);

        Vector<Item> items = instance.getItems();
        boolean foundItem = false;
        for (Item item : items) {
            if (item.getItemName().equals("computer")) {
                foundItem = true;
                break;
            }
        }
        // Verify that the method invocation returns the expected output
        assertTrue(foundItem);
    }

    /**
     * Test of bid method, of class Auction.
     * Testing the bid function to update an item’s highest bid for Auction class.
     */
    @Test
    public void testBid() throws Exception{
        System.out.println("Testing AuctionTest/testBid()...");
        UserManager umInstance = new UserManager();
        String createTrans1 = "01 user03 BS 0 teww";
        String createTrans2 = "01 user04 SS 0 teww";
        umInstance.createUser(createTrans1);
        umInstance.createUser(createTrans2);
        String addCredTrans1 = "03 user03 BS 100 teww";
        umInstance.addCredit(addCredTrans1);


        Auction instance = new Auction();
        String adItemTrans = "03 notebook user04 NA 50 10.0";
        instance.advertise(adItemTrans);

        String transaction = "04 notebook user04 user03 50.0";
        instance.bid(transaction);

        Vector<Item> items = instance.getItems();
        boolean bidValid = false;
        for (Item item : items) {
            if (item.getItemName().equals("notebook")) {
                if (item.getBuyerName().equals("user03") && item.getHighestBid() == 50.0) {
                    bidValid = true;
                    break;
                }
            }
        }

        // Verify that the method invocation returns the expected output
        assertTrue(bidValid);
    }

    /**
     * Test of delete method, of class Auction.
     * Testing the delete function to remove an item from the Auction class.
     */
    @Test
    public void testDelete() throws Exception{
        System.out.println("Testing AuctionTest/testDelete()...");
        UserManager umInstance = new UserManager();
        String createTrans = "01 user05 SS 0 teww";
        umInstance.createUser(createTrans);
        Vector<User> users = umInstance.getUsers();
        boolean foundUser = false;
        for (User user : users) {
            if (user.getUsername().equals("user05")) {
                foundUser = true;
                break;
            }
        }

        String deleteTrans = "02 user05 SS 0 teww";
        Auction instance = new Auction();
        umInstance.deleteUser(deleteTrans, instance);
        boolean deletedUser = true;
        for (User user : users) {
            if (user.getUsername().equals("user05")) {
                deletedUser = false;
                break;
            }
        }

        // Verify that the method invocation returns the expected output
        assertEquals(foundUser, deletedUser);
    }

    /**
     * Test of endDay method, of class Auction.
     * Testing the end of day function to decrement the remaining days of an item from the Auction class.
     */
    @Test
    public void testEndDay() throws Exception{
        System.out.println("Testing AuctionTest/testEndDay()...");
        Auction instance = new Auction();
        instance.load("Backend/iofiles/available_items.txt");
        UserManager users = new UserManager();
        instance.endDay(users);
    }

    /**
     * Test of write method, of class Auction.
     * Testing if the output to the available_items.txt is correct for Auction class.
     */
    @Test
    public void testWrite() throws Exception {
        System.out.println("Testing AuctionTest/testWrite()...");
        String filename = "item_testLog.txt";
        Auction instance = new Auction();
        instance.load("Backend/iofiles/available_items.txt");
        instance.write(filename);
    }

}
