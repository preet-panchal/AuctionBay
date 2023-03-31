// File input (AuctionBay/PHASE-5/Backend/iofiles): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-5/Backend/iofiles/new): new_available_items.txt, new_current_users_accounts.txt

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
		// write to new available_items file in the required format
		output = itemName + " " + sellerName + " " + buyerName + " " + String.format("%d", daysRemaining) + " " + String.format("%.2f", highestBid);
		System.out.println(output);
		return output;
	}
}



