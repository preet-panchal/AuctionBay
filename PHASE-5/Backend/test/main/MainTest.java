package main;

import main.Main;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    /**
     * Test of main method, of class Main.
     * Testing the initial main function call.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("Testing testMain()...");
        String[] args = null;
        Main.main(null);
    }
    
}
