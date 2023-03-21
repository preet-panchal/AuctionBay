// File input (AuctionBay/PHASE-4/Backend/): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-4/): new_available_items.txt, new_current_user_accounts.txt

package main;

// Item Class - stores the information for an individual auction
public class Item {
    // Private attributes
	private String itemName;
	private String sellerName;
	private String buyerName;
	private int daysRemaining;
        // max bid
	private double highestBid;
	
	// Constructor
	public Item(String _itemName, String _sellerName, String _buyerName, int _daysRemaining, double _highestBid) {
		itemName = _itemName;
		sellerName = _sellerName;
		buyerName = _buyerName;
		daysRemaining = _daysRemaining;
		highestBid = _highestBid;
	}
	
	// Methods
	
	// add methods getItemName() and getSellerName()
	public String getItemName() {
		return itemName;
	}
	
	// return seller name
	public String getSellerName() {
		return sellerName;
	}
    
	// return buyer name
	public String getBuyerName() {
		return buyerName;
	}
    
	// return highest bid
	public double getHighestBid() {
		return highestBid;
	}
    
	// return auction days remaining
	public int getDaysRemaining() {
		return daysRemaining;
	}
	
	// set auction days left
	public void setDaysRemaining(int days) {
		daysRemaining = days;
	}
	
	// updates the buyerName and highBid for an auction item
	public void newBid(String buyer, double bid) {
		buyerName = buyer;
		highestBid = bid;
	}
        
	// Creates a string in the correct format for the available items file.
	public String formatOutput() {
		String output;
		// a string with the format 'IIIIIIIIIIIIIIIIIII SSSSSSSSSSSSSSS UUUUUUUUUUUUUU DDD PPPPPP'
		// where IIIIIIIIIIIII is itemName, SSSSSSS is sellerName, UUUUU is buyerName, DDD is days remaining 
		// and PPPPP is highestBid

		// * TO-DO: Fix format to write to new_available_items.txt file according to documentation
		output = String.format("%-25s", itemName) + " " + String.format("%-15s", sellerName) + " " + String.format("%-15s", buyerName) + " " + String.format("%03d", daysRemaining) +" " + String.format("%06.2f", highestBid);
		System.out.println(output);
		return output;
	}
}



