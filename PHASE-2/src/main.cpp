/* Program: AuctionBay
 * Authors: Rija Baig, Preet Panchal, Eihab Syed, Nathaniel Tai
 * Description: Auction-style Sales Service console application.
*/

#include <string>
#include <iostream>
#include <sstream>
#include <algorithm>
#include <type_traits>
#include <typeinfo>

#include "Record.h"			// DEFINES RECORD STRUCTS & FUNCTIONS, AND CONSTANTS
#include "FileController.h" // HANDLES ALL FILE CONTROL
#include "User.h"			// HANDLES USER ACTIONS (PROMPTS) AND CURRENT USER INFORMATION
#include "AuctionSystem.h"	// HANDLES MENU, SESSION (LOGIN, LOGOUT)

using namespace std;

/*
	Main - Auction System Program
	@param int argc: The number of arguments passed
	@param: char** argv: Array of input files (format: current users, available items, daily transaction)
*/
int main(int argc, char** argv){
	// Initialize Variables
	bool auctionOpen = true;	// Whether or not to close (Exit) the auction system
	bool userFound = false;		// Whether or not the user exists (for login)

	string command;				// Stores user action (input)
	string transactionCode;		// Stores the current user actions transaction code (if any)
	string transactionDetails;  // Stores the transaction log (formatted information to append to transaction history)

	// Initialize Objects
	FileController fc(argv[1], argv[2], argv[3]);  // Takes in all the input files (accoutns file, items file, and transaction file)
	AuctionSystem auctionSys(true);				   // Initializing auction system by calling AuctionSystem class
	User currentUser;                              // Creating new user object	 					      

	cout << "Welcome to AuctionBay!\n--" << endl;
    cout << "Here is a list of operations (what to input):" << endl;
    cout << "- Login (login)\n" 
         << "- Create (create)\n"
         << "- Delete (delete)\n"
         << "- Advertise (advertise)\n"
         << "- Bid (bid)\n"
         << "- Refund (refund)\n"
         << "- Add Credit (addcredit)\n"
         << "- List All Items (listall)\n"
         << "- Logout (logout)\n" << endl;

	// Repeat until auction closes (exit command sets auction to closed)
	while (auctionOpen) {
		transactionCode.clear(); // Reset the transaction code
		// Prompt user to log in (repeat until user is logged in)
		while (!auctionSys.loggedIn) {
			string userInput;
			printf("Enter operation:\n");
			getline(cin, userInput);

			if (userInput == "login") {
				string username;	
				printf("Enter username:\n");
				getline(cin, username);

				USER_RECORD userRecord = fc.getUser(username); // Search for username in the users file
				if (!userRecord.username.empty()) {
					auctionSys.Login(userRecord, currentUser);
				} else {
					// printf("\nUser %s not found, please try again.", username.c_str());
					printf("\nError: This user does not exist in the user-accounts file.\n");
				}
			} else if (userInput == "logout") {
				return 0;
			} else {
				cout << "Error: You must login first.\n" << endl;
			}
		}

		// 	Asks user for new input of operation everytime until they logout
		printf("\nEnter operation:\n");
		getline(cin, command);
		transform(command.begin(), command.end(), command.begin(), ::tolower); // Transform command entered to lower-case
		
		// User enters Create operation - Create a new user account
		if (command == "create" && currentUser.accountType == ADMIN) {
		 	USER_RECORD newUserRecord = currentUser.CreateAccount(); // Create a new user record
			
			if (newUserRecord.accountType != "ER") {                 // Validating user privilege, making sure it is not default
				userFound = fc.findUser(newUserRecord.username); 	 // Check if user already exists

				if (userFound) {
					printf("Error: This user already exists in the user-accounts file.\n");
				} else {
					fc.addUser(newUserRecord);			// Add the new user record to the current users file
					printf("%s (%s) successfully created.\n", newUserRecord.username.c_str(), newUserRecord.accountType.c_str());

					transactionCode = CREATE_TRANSACTION_CODE;
					transactionDetails = recordToString(newUserRecord);
				}	
			} else {
				cout << newUserRecord.username << endl; // TO-DO: fix condition
			}
		}	

		// User enters Delete operation - Delete a user account
		else if (command == "delete" && currentUser.accountType == ADMIN) { // Verifies user access to operation
			string username = currentUser.DeleteAccount();
			USER_RECORD userRecord = fc.getUser(username);  // Retreive user record or user to delete

			if (!userRecord.username.empty()) {  // TO-DO: fix conditions for error handling
				if (userRecord.username != currentUser.username) {
					if (userRecord.accountType != ADMIN) {
						fc.deleteUser(username);		// Remove the user record from the current users file
						printf("%s successfully deleted.\n", username.c_str());

						transactionCode = DELETE_TRANSACTION_CODE;
						transactionDetails = recordToString(userRecord);
					} else {
						printf("Error: Only admin has privileges for this operation.\n");
					}
				} else {
					printf("Error: You cannot delete your own user.\n"); // TO-DO: fix error
				}
			} else {
				printf("Error: User does not exist in the user-accounts file.\n"); // TO-DO: fix error
			}
			cin.ignore();
		}

		// User enters Create operation - Put an item up for auction
		else if (command == "advertise") {
			if (currentUser.accountType == BUY_STANDARD) { 		// Validating user privilege, making sure it is not a buy standard account
				printf("Error: Only AA, FS, or SS have privileges for this operation.\n");
			} else {
				ITEM_RECORD itemRecord = currentUser.Advertise(); // Create a new item record
			
				if (itemRecord.duration != MAX_DURATION) {
					itemRecord.seller = currentUser.username;	   // Set the seller name to the current user
					itemRecord.buyer = "N/A";					   // Default buyer name
					fc.addItem(itemRecord);					       // Add the new item record to the available items file
					printf("%s successfully put up for auction for %d days.\n", itemRecord.itemName.c_str(), itemRecord.duration);

					transactionCode = ADVERTISE_TRANSACTION_CODE;
					transactionDetails = recordToString(itemRecord);
				}  
				cin.ignore();
			}
		}	

		// User enters bid operation - Bid on an item up for auction
		else if (command == "bid") {
			if (currentUser.accountType == SELL_STANDARD) {			// Validating user privilege, making sure it is not a sell standard account
				printf("Error: Only AA, FS, or BS have privileges for this operation.\n");
			} else {
				ITEM_RECORD itemRecord = currentUser.Bid(); 	  // Create item record for item to bid on
				if (itemRecord.duration > -1) {
					float highestBid = fc.getItemBid(itemRecord); // Find item and the highest bid currently on it (return -1 if item not found)

					if (highestBid > -1) {
						string amount;
						cout << "Current Highest Bid: $" << highestBid << endl;
						printf("Enter new bid amount:\n");
						cin >> amount;

						std::stringstream sstr(amount);
						float f;
						if (sstr >> f) {
							float famount = stof(amount);  // Convert the credit amount to a float
							if (typeid(famount) == typeid(float)) {
								float newBid = famount;
								highestBid += highestBid*0.05; // Increase the highest bid by 5% (requirement)

								if(newBid >= highestBid) {
									itemRecord.highestBid = newBid;
									fc.updateItemBid(itemRecord, currentUser.username);
									printf("Bid of $%f successfully placed on %s.\n", newBid, itemRecord.itemName.c_str());
									
									transactionCode = BID_TRANSACTION_CODE;
									transactionDetails = recordToString(itemRecord);
								} else { // TO-DO: fix error
									printf("Error: Your bid amount must be atleast 5%% greater than current highest bid.\n");	
								}
							}
						} else {
							std::printf("Error: Invalid input for bid amount.\n"); // TO-DO: fix error
						}
					} else {
						printf("Error: %s does not exist in available-items file.\n", itemRecord.itemName.c_str());
					}
				}
				cin.ignore();
			}
		}

		// User enters listall operation - List all all items in available items file
		else if (command == "listall") {
			fc.displayAvailableItems(); // Displays and formats current items file
		}

		else if (command == "refund") {
			// Validating user privilege, making sure it is an admin
			if (currentUser.accountType == FULL_STANDARD || currentUser.accountType == BUY_STANDARD || currentUser.accountType == SELL_STANDARD) {
				printf("Error: Only admin has privilages for this operation.\n");
			} else {
				REFUND_RECORD refundRecord = currentUser.Refund();	// Create refund record for buyer to get refund from seller

				if (refundRecord.amount != 0) {
					USER_RECORD buyerRecord = fc.getUser(refundRecord.buyer);
					USER_RECORD sellerRecord = fc.getUser(refundRecord.seller);

					if (!buyerRecord.username.empty() && !sellerRecord.username.empty()) {
						if (typeid(refundRecord.amount) == typeid(float)) {
							if (refundRecord.amount <= MAX_CREDIT) {
								buyerRecord.credit += refundRecord.amount;  // Add the refund amount to the buyers account
								sellerRecord.credit -= refundRecord.amount; // Remove the refund amount from the sellers account

								fc.updateCredit(buyerRecord.username, buyerRecord.credit);   // Update the buyers credit record
								fc.updateCredit(sellerRecord.username, sellerRecord.credit); // Update the sellers credit record

								printf("\n$%f refunded from %s to %s", refundRecord.amount, refundRecord.seller.c_str(), refundRecord.buyer.c_str());
								transactionCode = REFUND_TRANSACTION_CODE;
								transactionDetails = recordToString(refundRecord);
							} else {
								// printf("\nError - exceeded $%i refund limit.", MAX_CREDIT);
								printf("Error: Buyer's credit balance cannot surpass $999,999.00.\n"); // TO-DO: fix error
							}
						} else {
							printf("Error - credit amount must be a number.\n"); // TO-DO: fix error
						}	
					} else {
						printf("Error - Username pair not found.\n"); // TO-DO: fix error
					}
				}
				cin.ignore();
			}
		}	

		// User enters addcredit operation - Add a credit amount to a user
		else if (command == "addcredit") {
			std::string username;
			if (currentUser.accountType == ADMIN) {
				std::cout << "Enter the username to add credit to:\n";
				std::cin >> username;
			} else {
				username = currentUser.username;
			}
			userFound = fc.findUser(username);
			if (userFound) {
				float newCredit = currentUser.AddCredit(); // Get a new credit amount for the user
				if (newCredit == currentUser.credit) {
					fc.updateCredit(username, newCredit); // Update users credit in the current users file

					transactionCode = ADDCREDIT_TRANSACTION_CODE;
					transactionDetails = currentUser.toString();
				}	
			} else { // TO-DO: fix error
				printf("Error: The user does not exist in the user-accounts file.\n");
			}
			cin.ignore();
		}	

		// User enters logout operation - Close the current users session
		else if (command == "logout") {
			transactionCode = LOGOUT_TRANSACTION_CODE;
			transactionDetails = currentUser.toString(); // Write logout to daily transaction
			auctionOpen = false;			 // Bool changes to false as system closes
			auctionSys.Logout(currentUser);  // Logout user from auction system
			currentUser = User();            // Reset current user information
			return 0;
		}	

		// Handles any case where user inputs any operation other than the above conditionals
		else {
			cout << "Invalid input for operation. Please try again." << endl;
		}

		// TO-DO: extensive testing
		if (!transactionCode.empty()) {
			fc.logTransaction(transactionCode, transactionDetails); // Log all daily transactions
		}
	}
    return 0;
}