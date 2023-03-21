// File input (AuctionBay/PHASE-4/Backend/): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-4/): new_available_items.txt, new_current_user_accounts.txt

package main;

// User Class - Contains individual user information and methods
public class User {
	// Private attributes
	private String username;
	private String accountType;
	private double creditAmount;
	private String password;
	
	// Constructor
	public User(String _username, String _accountType, double _creditAmount, String _password) {
		username = _username;
		accountType = _accountType;
		creditAmount = _creditAmount;
		password = _password;
	}
	
	// Returns the users username
	public String getUsername(){
		return username;
	}

	// Creates a string in the correct format for the active user file
	public void setUsername(String username) {
		this.username = username;
	}
	
	// Adds the value passed as a parameter to the creditAmount
	public void updateCreditAmount(double credit) {
		creditAmount += credit;
	}

	// Returns the users account type
	public String getAccountType() {
		return accountType;
	}

	// Sets the users account type
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	// Returns the users total credit amount
	public double getCreditAmount() {
		return creditAmount;
	}

	// Sets the users credit amount
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	// Returns the users password
	public String getPassword() {
		return password;
	}

	// Sets the users password
	public void setPassword(String password) {
		this.password = password;
	}

	// Creates a string in the correct format for the active user file.
	public String formatOutput() {
		String output;
		// a string in the format 'UUUUUUUUUUUUUUU TT CCCCCCCCC'
		// where UUUUUUUUUUU is the username, TT is acountType, and CCCCCCCCC is creditAmount		

		// * TO-DO: Fix format to write to new_current_user_accounts.txt file according to documentation
		output = String.format("%1$" + -15 + "s", username) + " " + accountType + " " + String.format("%09.2f", creditAmount);
		return output;
	}
}
