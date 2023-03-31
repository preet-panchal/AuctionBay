// File input (AuctionBay/PHASE-5/Backend/iofiles): daily_transaction.txt, available_items.txt, current_user_accounts.txt
// File output (AuctionBay/PHASE-5/Backend/iofiles/new): new_available_items.txt, new_current_users_accounts.txt

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
		// write to the current_users_accounts file in the required format
		output = username + " " + accountType + " " + String.format("%.2f", creditAmount) + " " + password;
		System.out.println(output);
		return output;
	}
}
