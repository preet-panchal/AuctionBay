// File input (AuctionBay/PHASE-4/Backend/): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-4/): new_available_items.txt, new_current_user_accounts.txt

package main;

import java.util.*;
import java.io.*;

// Auction Class -- handles each auctioned item and auction operations line-by-line
public class Auction {
	private Vector<Item> items;

    // constructor
    public Auction() {
        items = new Vector<Item>();
	}
    
    // display all auction items to console as a message
    public void displayAuctions() {
        // * TO-DO: Fix console message. *
        System.out.println("Printing all AUCTIONS");
        int i = 0;
        while( i < items.size()) {
            items.get(i).formatOutput();
            i++;
        }
    }
    
    // delete item from available_items file if auction is complete or user is deleted
    public void deleteAllAuctions(String username) {
        int i = 0;
        while (i < items.size()) {
            if(items.get(i).getSellerName().equals(username)) {
                items.remove(i);
                break;
            }
            i++;
        }
        // * TO-DO: Fix console message. *
        System.out.println("All of " + username.trim() + " auctions have been deleted.");
    }

	// loads the auctions from a file to the vector
	public void load(String filename) throws FileNotFoundException {
        // loop through the file creating an auction object for each record and adding them to the vector
        // * TO-DO: Fix console message. *
        System.out.println("Load all auctions");
        File file = new File(filename);
        Scanner input = null;

        input = new Scanner(file);

        List<String> list = new ArrayList<String>();

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }
        
        // Populate auctions vector from file list
        for (String item : list) {
            String[] splitStr = item.split(" ");
            String itemName = splitStr[0];
            String sellerName = splitStr[1];
            String buyerName = splitStr[2];
            int daysRemaining = Integer.parseInt(splitStr[3]);
            // max bid
            double highestBid = Double.parseDouble(splitStr[4]);
            
            Item newItem = new Item(itemName, sellerName, buyerName, daysRemaining, highestBid);
            items.add(newItem);
        }

        input.close();
	}
	
	// adds a new auction to the vector
	public void advertise(String transaction) {
        // parse the transaction to create an auction object and add it to the vector
        System.out.println("Create advertisement");
        String[] splitStr = transaction.split(" ");

        String itemName = splitStr[1];
        String sellerName = splitStr[2];
        String buyerName = "N/A";
        int daysRemaining = Integer.parseInt(splitStr[2]);
        double highestBid = Double.parseDouble(splitStr[3]);
        Item newItem  = new Item(itemName, sellerName, buyerName, daysRemaining, highestBid);
        items.add(newItem);
	}
	
	// updates an auction through newBid
	public void bid(String transaction) {
        // parse the transaction to  identify the element in the vector that needs to be updated with findAuction
        // and update its buyer and high bid through newBid()
        System.out.println("create bid");
        String[] splitStr = transaction.split(" ");

        String itemName = splitStr[1]; 
        String sellerName = splitStr[2];
        String buyerName = splitStr[3];
        double highestBid = Double.parseDouble(splitStr[5]);

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getSellerName().trim().equals(sellerName.trim()) && items.get(i).getItemName().trim().equals(itemName.trim())) {
                // if bid > maxbid
                    // close aution
                // else
                items.get(i).newBid(buyerName, highestBid);
                // * TO-DO: Fix console message. *
                System.out.println("Bid transaction completed");
                break;
            }
        }
	}
	
	// subtracts 1 from the days remaining for each auction if the daysRemaining becomes 0 close the auction
	public UserManager endDay(UserManager userManager) {
        // * TO-DO: Add conditional to validate that there is never a negative number of daysRemaining for any Auciton. *
        for (int i = 0; i < items.size(); i++) {
             // loop through the vector decreasing the daysRemaining for each Auction in the list
            int daysRemaining = items.get(i).getDaysRemaining() - 1;
            items.get(i).setDaysRemaining(daysRemaining);
        }
        // * TO-DO: Fix console message. *
        System.out.println("All items decremented");

        return userManager;
	}
	
	// outputs the formated items to the new available items file
	public void write(String filename) throws FileNotFoundException, UnsupportedEncodingException {
        try ( // loop through the vector writing the formatOutput value for each auction to the file
            PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            int i = 0;
            while ( i < items.size()) {
                writer.println(items.get(i).formatOutput());
                i++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // * TO-DO: Fix console message. *
        System.out.println("All items have been written to file.");
	}
	// add findAuction function to search the vector for an item with a item name and seller name and returns the index
}
