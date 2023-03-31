/* Program: AuctionBay -- Phase 5
 * Authors: Rija Baig, Preet Panchal, Eihab Syed, Nathaniel Tai
 * Description: Auction-style Sales Service -- Backend Application.
*/

// File input (AuctionBay/PHASE-5/Backend/iofiles): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-5/Backend/iofiles/new): new_available_items.txt, new_current_user_accounts.txt

package main;

import java.io.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        // Reading files in Backend folder from daily Frontend run
        String mergedTransFile = "Backend/iofiles/daily_transaction.txt";
        String oldUserAccFile = "Backend/iofiles/current_users_accounts.txt";
        String oldAvailItemsFile = "Backend/iofiles/available_items.txt";
        
        // open current_users_accounts file for reading
        UserManager userManager = new UserManager();
		userManager.load(oldUserAccFile);
        userManager.displayUsers(); 
		
        // open available_items file for reading
		Auction auction = new Auction();
		auction.load(oldAvailItemsFile);
		auction.displayAuctions();
        
        // store each individual transaction
        String transaction;
        
        try {
            // read merged daily_transaction file line-by-line
            BufferedReader bufferreader = new BufferedReader(new FileReader(mergedTransFile));
            transaction = bufferreader.readLine();
            
            // handle function cals for each in operation
            while (transaction != null) {     
                String[] splitStr = transaction.split("\\s+");
                System.out.println("tranaction: " + splitStr[0]);
                if (Objects.equals(splitStr[0], "00")) {
                    break;
                } else if (Objects.equals(splitStr[0], "01")) {
                    // create user
                    userManager.createUser(transaction);
                } else if (Objects.equals(splitStr[0], "02")) {
                    // delete user
                    userManager.deleteUser(transaction, auction);
                } else if (Objects.equals(splitStr[0], "03")) {
                    // advertise
                    auction.advertise(transaction);
                } else if (Objects.equals(splitStr[0], "04")) {
                    // bid
                    auction.bid(transaction);
                } else if (Objects.equals(splitStr[0], "05")) {
                    // refund
                    userManager.refund(transaction);
                } else if (Objects.equals(splitStr[0], "06")) {
                    // addcredit
                    userManager.addCredit(transaction);
                } else if (Objects.equals(splitStr[0], "07")) {
                    // need to create
                    userManager.resetPassword(transaction);
                }
                
                transaction = bufferreader.readLine();
            }
            bufferreader.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // subtract the duration of each item by one after each run
        auction.endDay(userManager);
        // write to new accounts file and available items file
        auction.write("Backend/iofiles/new/new_available_items.txt");
        userManager.write("Backend/iofiles/new/new_current_users_accounts.txt");
	}
}
