package main;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * items Container Class
 * the list of all the available items
 */
public class Auction {
	// Private attributes
	private Vector<Item> items;
    public Auction() {
            items = new Vector();
	}

    public void readFile(String filename) {
        String oldUserAccFile = "/Users/preetpanchal/Desktop/WINTER 2023/CSCI3060U/GROUP PROJECT/AuctionBay/PHASE-4/Backend/available_items.txt";
        FileController fc = new FileController();
        fc.load(oldUserAccFile);
    }
        
    public void displayAuctions() {
        System.out.println("Printing all AUCTIONS");
        int i = 0;
        while( i < items.size()) {
            items.get(i).formatOutput();
            i++;
        }
    }
    
    public void deleteAllAuctions(String username) {
        int i = 0;
        while (i < items.size()) {
            if(items.get(i).getSellerName().equals(username)) {
                items.remove(i);
                break;
            }
            i++;
        }
        System.out.println("All of " + username.trim() + " auctions have been deleted.");
    }
	
	// Methods
	//loads the auctions from a file to the vector
	public void load(String filename) throws FileNotFoundException {
            //loop through the file creating an auction object for each record and adding them to the vector
            System.out.println("Load all auctions");
            File file = new File(filename);
            Scanner input = null;

            input = new Scanner(file);

            List<String> list = new ArrayList<String>();

            while (input.hasNextLine()) {
                list.add(input.nextLine());
            }
            
            //Populate auctions vector from file list
            for (String item : list) {
                String[] splitStr = item.split(" ");
                String itemName = splitStr[0];
                String sellerName = splitStr[1];
                String buyerName = splitStr[2];
                int daysRemaining = Integer.parseInt(splitStr[3]);
                //max bid
                double highestBid = Double.parseDouble(splitStr[4]);
                
                Item newItem = new Item(itemName, sellerName, buyerName, daysRemaining, highestBid);
                items.add(newItem);
            }
	}
	
	//adds a new auction to the vector
	public void advertise(String transaction){
            //parse the transaction to create an auction object and add it to the vector
            System.out.println("Create advertisement");

            String itemName = transaction.substring(3, 27);
            String sellerName = transaction.substring(29, 43);
            String buyerName = sellerName;
            int daysRemaining = Integer.parseInt(transaction.substring(45, 47));
            double highestBid = Double.parseDouble(transaction.substring(49, 55));
            Item newItem  = new Item(itemName, sellerName, buyerName, daysRemaining, highestBid);
            items.add(newItem);
	}
	
	//updates an auction through newBid
	public void bid(String transaction){
            //parse the transaction to  identify the element in the vector that needs to be updated with findAuction
            //and update its buyer and high bid through newBid()
            System.out.println("create bid");

            String itemName = transaction.substring(3, 23); 
            String sellerName = transaction.substring(25, 40);
            String buyerName = transaction.substring(42, 57);
            double highestBid = Double.parseDouble(transaction.substring(59, 65));

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getSellerName().trim().equals(sellerName.trim()) && items.get(i).getItemName().trim().equals(itemName.trim())) {
                    //if bid > maxbid
                        //close aution
                    //else
                    items.get(i).newBid(buyerName, highestBid);
                    System.out.println("Bid transaction completed");
                    break;
                }
            }
	}
	
	//removes an auction from the list
	public void delete(String item, String seller){
            //locates the element with the matching item name and seller name and removes it from the vector
            
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getSellerName().equals(seller) && items.get(i).getItemName().equals(item)) {
                    System.out.println("AUCTION DELETED");
                    //Auction auctionToRemove = new Auction(item, seller, items.get(i).getBuyerName(), items.get(i).getDaysRemaining(), items.get(i).getHighestBid());
                    //items.remove(auctionToRemove);
                    items.remove(i);
                    break;
                }
            }
	}
	
	//subtracts 1 from the days remaining for each auction if the daysRemaining becomes 0 close the auction
	public UserManager endDay(UserManager userManager) {
            //loop through the vector decreasing the daysRemaining for each Auction in the list
            //when days remeaining hit 0 use the closeAuction method of Users with the sellerName, buyerName, and highestBid
            //then delete the auction from the vector

            for (int i = 0; i < items.size(); i++) {
                int daysRemaining = items.get(i).getDaysRemaining() - 1;
                if(daysRemaining == 0){
                    String itemName = items.get(i).getItemName();
                    String buyerName = items.get(i).getBuyerName();
                    String sellerName = items.get(i).getSellerName();
                    Double highestBid = items.get(i).getHighestBid();
                    System.out.println("Buyer: " + buyerName + " Seller: " + sellerName + " Credit: " + highestBid);

                    //TODO: Add closeAuction from Users
                    userManager.closeAuction(buyerName, sellerName, highestBid);
                    delete(itemName, sellerName);
                }else{
                    items.get(i).setDaysRemaining(daysRemaining);
                }
            }
            System.out.println("All items decremented");

            return userManager;
	}
	
	//outputs the formated items to the new available items file
	public void write(String filename) throws FileNotFoundException, UnsupportedEncodingException {
            try ( //loop through the vector writing the formatOutput value for each auction to the file
                PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
                int i = 0;
                while( i < items.size()) {
                    writer.println(items.get(i).formatOutput());
                    i++;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.out.println("All items have been written to file.");
	}
	//add findAuction function to search the vector for an item with a item name and seller name and returns the index
}
