package main;

import main.Item;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {
    /**
     * Test of getItemName method, of class Item.
     * Testing the getter for an item’s name in the Item class.
     */
    @Test
    public void testGetItemName() {
        System.out.println("Testing ItemTest/testGetItemName()...");
        Item instance = new Item("itemname", "test", "test", 1, 1.0);
        String expResult = "itemname";
        String result = instance.getItemName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSellerName method, of class Item.
     * Testing the getter for an item’s seller in the Item class.
     */
    @Test
    public void testGetSellerName() {
        System.out.println("Testing ItemTest/testGetSellerName()...");
        Item instance = new Item("itemname", "test", "test", 1, 1.0);
        String expResult = "test";
        String result = instance.getSellerName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBuyerName method, of class Item.
     * Testing the getter for an item’s buyer in the Item class.
     */
    @Test
    public void testGetBuyerName() {
        System.out.println("Testing ItemTest/testGetBuyerName()...");
        Item instance = new Item("itemname", "test", "test", 1, 1.0);
        String expResult = "test";
        String result = instance.getBuyerName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHighestBid method, of class Item.
     * Testing the getter for an item’s highest bid in the Item class.
     */
    @Test
    public void testGetHighestBid() {
        System.out.println("Testing ItemTest/testGetHighestBid()...");
        Item instance = new Item("itemname", "test", "test", 1, 1.0);
        double expResult = 1.0;
        double result = instance.getHighestBid();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getDaysRemaining method, of class Item.
     * Testing the getter for an item’s remaining days in the Item class.
     */
    @Test
    public void testGetDaysRemaining() {
        System.out.println("Testing ItemTest/getDaysRemaining()...");
        Item instance = new Item("itemname", "test", "test", 1, 1.0);
        int expResult = 1;
        int result = instance.getDaysRemaining();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDaysRemaining method, of class Item.
     * Testing the setter for an item’s remaining days in the Item class.
     */
    @Test
    public void testSetDaysRemaining() {
        System.out.println("Testing ItemTest/SetDaysRemaining()...");
        int days = 5;
        Item instance = new Item("itemname", "test", "test", 1, 1.0);
        instance.setDaysRemaining(days);
        int expResult = 5;
        int result = instance.getDaysRemaining();
        assertEquals(expResult, result);
    }

    /**
     * Test of newBid method, of class Item.
     * Testing the new bid function for an item to verify the new highest bid and buyer in Item class.
     */
    @Test
    public void testNewBid() {
        System.out.println("Testing ItemTest/testNewBid()...");
        String buyer = "test2";
        double bid = 5.0;
        Item instance = new Item("itemname", "test", "test", 1, 1.0);
        instance.newBid(buyer, bid);
        
        double expResult = 5.0;
        double result = instance.getHighestBid();
        assertEquals(expResult, result, 0.0);
        
        String expResult2 = "test2";
        String result2 = instance.getBuyerName();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of formatOutput method, of class Auction.
     * Testing if the output to the available_items.txt is correct for Auction class.
     */
    @Test
    public void testFormatOutput() {
        System.out.println("Testing ItemTest/testFormatOutput()...");
        Item instance = new Item("notebook", "user01", "user03", 5, 10);
        String expResult = "notebook user01 user03 5 10.00";
        String result = instance.formatOutput();
        assertEquals(expResult, result);
    }
}