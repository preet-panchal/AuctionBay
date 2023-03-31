// File input (AuctionBay/PHASE-5/Backend/iofiles): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-5/Backend/iofiles/new): new_available_items.txt, new_current_users_accounts.txt

package main;

import java.io.*;
import java.util.Objects;
import java.util.Vector;

// UserManager Class -- handles each user operations line-by-line
public class UserManager {
	private Vector<User> users;

    // constructor
	public UserManager() {
		users = new Vector<User>();
	}

    public Vector<User> getUsers() {
        return this.users;
    }
	
	// validate if string read is a double (for credit values)
	public double validateDouble(String doubleStr) {
        double creditD = 0;
        try{
            creditD = Double.parseDouble(doubleStr);
        } catch(NumberFormatException ex) { // handle your exception
            ex.printStackTrace();
        }
        return creditD;
	}

    // displays all users to console as a message
    public Vector<User> displayUsers() {
        // * TO-DO: Fix console message. *
        System.out.println("-- Displaying all active Users --");
        int i = 0;
        while( i < users.size()) {
            users.get(i).formatOutput();
            i++;
        }
        return this.users;
    }
	
	// loads the user objects from the file into the vector
	public void load(String filename) {
        // loop through the file creating a user object for each record and adding them to the vector
        String userFile;
        String usernameIn;
        String accountTypeIn;
        double creditAmountIn;
        String password;

        System.out.println("-- Reading from current_users_accounts.txt (from Frontend) --");

        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(filename));
            userFile = bufferreader.readLine();

            while (userFile != null) { 
                String[] splitStr = userFile.split(" ");
                usernameIn = splitStr[0];
                accountTypeIn = splitStr[1];
                creditAmountIn = validateDouble(splitStr[2]);
                password = splitStr[3];
                
                User newUser = new User(usernameIn, accountTypeIn, creditAmountIn, password);
                users.add(newUser);
            
                userFile = bufferreader.readLine();
            }
            bufferreader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	// adds a newly created User to the vector
	public void createUser(String transaction) {
		// parses the transaction and creates a new user object and adds it to the vector
		// String[] splitStr = transaction.split("\\s+");

        String[] splitStr = transaction.split(" ");
        String username = splitStr[1];
        String accountType = splitStr[2];
        Double credit = validateDouble(splitStr[3]);
        String password = splitStr[4];

        // Check if new user created has a username that is different from all existing users.
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                System.out.println("ERROR: New user created must have a different username from all existing users.\n" +
                        "--- For Transaction: " + transaction.toString());
                return;
            }
        }
        User createdUser = new User(username, accountType, credit, password);
        users.add(createdUser);
        // * TO-DO: Fix console message. *
		System.out.println("-- User " + username + " has been created.");
    }
	
	// finds and removes a User from the vector
	public Auction deleteUser(String transaction, Auction auction) throws IOException {
        // parse the transaction and removes the user object with a matching username from the vector
        // String[] splitStr = transaction.split("\\s+");
        String[] splitStr = transaction.split(" ");
        String username = splitStr[1];

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
                // * TO-DO: Fix console message. *
                System.out.println("-- User " + username + " has been deleted.");
                break;
            }
        }
        auction.deleteAllAuctions(username);
        
        return auction;
	}
	
	// finds the 2 users and updates their credit amount
	public void refund(String transaction) {
        // parses the transaction then uses findUser to find both users then use the updateCredits for both users
        // switching the sign where appropriate 
        String[] splitStr = transaction.split(" ");

        String buyerName = splitStr[1];
        String sellerName = splitStr[2];
        Double credit = validateDouble(splitStr[3]);


        for (int i = 0; i < users.size(); i++) {
            // validating buyer credit balance
            if (users.get(i).getUsername().equals(buyerName)) {
                users.get(i).updateCreditAmount(credit);
            } // validating seller credit balance
            else if (users.get(i).getUsername().equals(sellerName)) {
                users.get(i).updateCreditAmount(-credit);    
            }
        }

        System.out.println("-- Refund transaction completed --");
        System.out.println(sellerName + " refunded " + credit + " to " + buyerName + ".");
	}
	
	// finds a user and updates their credit amount
	public void addCredit(String transaction) {
        // parse the transaction then use findUser to locate the correct user object then use updateCredit for 
        // that user to add the credit 
        String[] splitStr = transaction.split(" ");
        String username = splitStr[1];
        Double credit = validateDouble(splitStr[3]);

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.get(i).updateCreditAmount(credit);

                System.out.println("-- Addcredit transaction completed --");
                System.out.println(credit + " has been added to " + username + " balance.");
                break;
            }
        }
	}

    // finds a user and resets their password
	public void resetPassword(String transaction) { 
        // parse the transaction then use findUser to locate the correct user object then use resetPassword for that user 
        String[] splitStr = transaction.split(" ");
        String username = splitStr[1];
        String password = splitStr[2];
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.get(i).setPassword(password);

                System.out.println("-- Reset Password transaction completed --");
                System.out.println(username + " password has been reset.");
                break;
            }
        }
	}
	
	// transfers the funds from the buyer to the seller when the auction ends
	public void closeAuction(String buyer, String seller, double amount) {
        // use findUser to locate the buyer and seller object then use the updateCredit method for each object to 
        // transfer the amount from the buyer to the seller
        for (User user : users) {
            // add money to seller
            if (user.getUsername().equals(seller)) {
                user.updateCreditAmount(amount);
            }// subtract credit from buyer
            else if (user.getUsername().equals(buyer)) {
                user.updateCreditAmount(amount * -1);
            }
        }
        System.out.println("-- Auction is now closed --");
	}
	
	// writes the formated users to the output file
	public void write(String filename) throws FileNotFoundException, IOException {
        // loops through the vector and writes the formatOutput string from each user object to the file
        System.out.println("-- Updated user info written to new_current_users_accounts.txt --");
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        int i = 0;
        while( i < users.size()) {
                writer.println(users.get(i).formatOutput());
                i++;
        }
        writer.close();
    }
}
