// File input (AuctionBay/PHASE-5/Backend/iofiles): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-5/Backend/iofiles/new): new_available_items.txt, new_current_users_accounts.txt

package main;

import java.util.*;
import java.io.*;

// Auction Class -- handles each auctioned item and auction operations line-by-line
public class Auction {
	public Vector<Item> items;

    // constructor
    public Auction() {
        items = new Vector<Item>();
	}

    public Vector<Item> getItems() {
        return this.items;
    }
    
    // display all auction items to console as a message
    public void displayAuctions() {
        System.out.println("-- Displaying all active Auctions --");
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
        System.out.println("-- Delete Auctions of a Deleted User --");
        System.out.println("All of " + username.trim() + " auctions have been deleted.");
    }

	// loads the auctions from a file to the vector
	public void load(String filename) throws FileNotFoundException {
        // loop through the file creating an auction object for each record and adding them to the vector
        System.out.println("-- Reading from available_items.txt (from Frontend) --");
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
	public String advertise(String transaction) {
        // parse the transaction to create an auction object and add it to the vector
        String[] splitStr = transaction.split(" ");

        String itemName = splitStr[1];
        String sellerName = splitStr[2];
        String buyerName = "N/A";
        int daysRemaining = Integer.parseInt(splitStr[4]);
        double highestBid = Double.parseDouble(splitStr[5]);
        Item newItem  = new Item(itemName, sellerName, buyerName, daysRemaining, highestBid);
        items.add(newItem);

        System.out.println("-- Advertise transaction completed --");
        System.out.println(sellerName + " put up " + itemName + " for auction.");

        return itemName;
    }
	
	// updates an auction through newBid
	public void bid(String transaction) {
        // parse the transaction to  identify the element in the vector that needs to be updated with findAuction
        // and update its buyer and high bid through newBid()
        String[] splitStr = transaction.split(" ");

        String itemName = splitStr[1]; 
        String sellerName = splitStr[2];
        String buyerName = splitStr[3];
        double highestBid = Double.parseDouble(splitStr[4]);

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getSellerName().trim().equals(sellerName.trim()) && items.get(i).getItemName().trim().equals(itemName.trim())) {
                items.get(i).newBid(buyerName, highestBid);
                break;
            }
        }
        System.out.println("-- Bid transaction completed --");
        System.out.println(buyerName + " placed a bid of " + highestBid + " on " + itemName + ".");
	}

    // removes an auction from the list
    public void delete(String item, String seller) {
        // locates the element with the matching item name and seller name and removes it from the vector
        System.out.println("-- Item Deleted from Auction --");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getSellerName().equals(seller) && items.get(i).getItemName().equals(item)) {
                System.out.println(items.get(i).getItemName() + " deleted from the auction.");
                items.remove(i);
                break;
            }
        }
    }
	
	// subtracts 1 from the days remaining for each auction if the daysRemaining becomes 0 close the auction
	public UserManager endDay(UserManager userManager) {
        // when days remaining hit 0 use the closeAuction method of Users with the sellerName, buyerName, and highestBid
        // then delete the auction from the vector
        for (Item item : items) {
            int daysRemaining = item.getDaysRemaining() - 1;
            if (daysRemaining == 0) {
                String itemName = item.getItemName();
                String buyerName = item.getBuyerName();
                String sellerName = item.getSellerName();
                Double highestBid = item.getHighestBid();

                userManager.closeAuction(buyerName, sellerName, highestBid);
                delete(itemName, sellerName);
            } else if (daysRemaining < 0) {
                //  Check if item should ever have a negative number of days left
                System.out.println("ERROR: No item should ever have a negative number of days left.");
            } else {
                item.setDaysRemaining(daysRemaining);
            }
        }
        System.out.println("-- Auction EOD changes effective --");

        return userManager;
	}

	// outputs the formatted items to the new available items file
	public void write(String filename) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("-- Updated user info written to new_available_items.txt --");
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
	}
}
