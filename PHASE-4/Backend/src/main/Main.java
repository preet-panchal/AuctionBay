package main;

import java.io.*; 

public class Main {

    public static void main(String[] args) throws IOException {
        String mergedTransFile = "/Users/preetpanchal/Desktop/WINTER 2023/CSCI3060U/GROUP PROJECT/AuctionBay/PHASE-4/Backend/daily_transaction.txt";
        String oldUserAccFile = "/Users/preetpanchal/Desktop/WINTER 2023/CSCI3060U/GROUP PROJECT/AuctionBay/PHASE-4/Backend/current_users_accounts.txt";
        String oldAvailItemsFile = "/Users/preetpanchal/Desktop/WINTER 2023/CSCI3060U/GROUP PROJECT/AuctionBay/PHASE-4/Backend/available_items.txt";
        
        // FileController fc = new FileController();
        // fc.load(mergedTransFile);
        // fc.load(oldUserAccFile);
        // fc.load(oldAvailItemsFile);
        
        UserManager userManager = new UserManager();
		userManager.load(oldUserAccFile);
        userManager.displayUsers(); 
		
		Auction auction = new Auction();
		auction.load(oldAvailItemsFile);
		auction.displayAuctions();

        String transaction;
        try {

            BufferedReader bufferreader = new BufferedReader(new FileReader(mergedTransFile));
            transaction = bufferreader.readLine();

            while (transaction != null) {     
                
                String[] splitStr = transaction.split("\\s+");
                System.out.println("tranaction: " + splitStr[0]);
                if (splitStr[0] == "00") {
                    break;
                } else if (splitStr[0] == "01") {
                    // create user
                    userManager.createUser(transaction);
                } else if (splitStr[0] == "02") {
                    // delete user
                    userManager.addCredit(transaction);
                } else if (splitStr[0] == "03") {
                    // advertise
                    auction.advertise(transaction);
                } else if (splitStr[0] == "04") {
                    // bid
                    auction.bid(transaction);
                } else if (splitStr[0] == "05") {
                    // refund
                    userManager.refund(transaction);
                } else if (splitStr[0] == "06") {
                    // addcredit
                    userManager.addCredit(transaction);
                } else if (splitStr[0] == "07") {
                    // need to create
                    // userManager.resetPassword(transaction);
                    System.out.println(transaction);
                }
                
                transaction = bufferreader.readLine();
            }
            bufferreader.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        auction.endDay(userManager);
        auction.write("auctions.txt");
        userManager.write("usernames.txt");
	}
}
