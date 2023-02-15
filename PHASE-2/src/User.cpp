#include "User.h"
#include <string>
#include <iostream>
#include <sstream>
#include <type_traits>
#include <typeinfo>

// Returns current user information as a string
std::string User::toString(){
	std::stringstream userStream;
	userStream << username << " " << accountType << " " << credit;
	std::string userInfo = userStream.str();
	return userInfo;
}

USER_RECORD User::CreateAccount() {
	bool userExists = false;	// Used to check if user already exists
	USER_RECORD newUser;		// Initialize new USER_RECORD for newly created user

	std::printf("Enter a username to create: ");
	std::cin >> newUser.username;
	std::printf("Enter an user type: ");
	std::cin >> newUser.accountType;

	// TO-DO: need to fix error catching
	if (newUser.username.length() > 15){
		newUser.username = "Error: Username must be only 15 characters in max length.\n";
		newUser.accountType = "ER";
	} else if (newUser.accountType != "AA" && newUser.accountType != "FS" && newUser.accountType != "BS" && newUser.accountType != "SS"){
		newUser.username = "Error: Invalid user type. (Must be: AA, FS, BS or SS)\n";
		newUser.accountType = "ER";
	}
	std::cin.ignore();

	return newUser;
}

std::string User::DeleteAccount() {
	std::string username;
	std::printf("Enter username to delete: ");
	std::cin >> username;

	return username;
}

ITEM_RECORD User::Advertise() {
	ITEM_RECORD itemRecord;		// Initialize new ITEM_RECORD for newly created item
	std::string minBid;
	std::string duration;

	std::printf("Enter the item name: ");
	std::cin >> itemRecord.itemName;
	// TO-DO: fix error
	if (itemRecord.itemName.length() > 25 || itemRecord.itemName.length() < 1) {
		itemRecord.duration = 999;
		std::printf("Error: Item name must be between 1-25 characters in length.\n");
		return itemRecord;
	}
	std::cin.clear();

	std::printf("Enter the minimum bid ($): ");
	std::cin >> itemRecord.minBid;
	// TO-DO: fix error
	if (itemRecord.minBid > 999.99 || itemRecord.minBid < 0) { 
		itemRecord.duration = 999;
		std::printf("Error: Minimum bid amount must be betwceen 0-999.99.\n");
		return itemRecord;
	} 
	// if (is_number(itemRecord.minBid) == false) {
	// 	itemRecord.duration = 999;
	// 	std::printf("Error: Minimum bid amount must be a number.\n");
	// 	return itemRecord; 
	// } else {
	// 	if (itemRecord.minBid > 999.99 || itemRecord.minBid < 0) { 
	// 		itemRecord.duration = 999;
	// 		std::printf("Error: Minimum bid amount must be betwceen 0-999.99.\n");
	// 		return itemRecord;
	// 	} 
	// 	itemRecord.minBid = std::stoi(minBid);
	// }
	std::cin.clear();

	std::printf("Enter auction duration (in days): ");
	std::cin >> itemRecord.duration;
	// TO-DO: fix error
	if (itemRecord.duration > 100 || itemRecord.duration < 1 ) {
		itemRecord.duration = 999;
		std::printf("Error: Auction duration must be between 1-100 days.\n");
		return itemRecord;
	}   
	// if (is_number(itemRecord.duration) == false) {
	// 	itemRecord.duration = 999;
	// 	std::printf("Error: Auction duration must be a number.\n");
	// 	return itemRecord; 
	// } else {
	// 	if (itemRecord.duration > 100 || itemRecord.duration < 1 ) {
	// 		itemRecord.duration = 999;
	// 		std::printf("Error: Auction duration must be between 1-100 days.\n");
	// 		return itemRecord;
	// 	}   
	// 	itemRecord.duration = std::stoi(duration);
	// }

	return itemRecord;
}

// TO-DO: addcredit does not function as intended
float User::AddCredit() {
	std::string amount;	
	std::cout << "Enter the credit amount to add: ";
	std::cin >> amount;

	std::stringstream sstr(amount);
	float f;
	if (sstr >> f) {
		float famount = std::stof(amount);
		if (typeid(famount) == typeid(float)) {
			if (famount < MAX_CREDIT_ADD) {
				// TO-DO: fix conditions
				if ((credit+famount) < MAX_CREDIT) {
					credit += famount;
					// std::cout << "\nSuccessfully added $ " << amount << std::endl;
					// std::cout << "\nCredit is now: " << credit << std::endl;
					std::cout << "New credit balance: $" << credit << std::endl; 
				} else {
					std::printf("Error - exceeded $%i credit limit.\n", MAX_CREDIT); 
				}
			} else { // TO-DO: fix error
				// std::printf("\nError - exceeded $%i session limit.", MAX_CREDIT_ADD);
				std::printf("Error: Invalid input for credit. Enter a number between 1-1000.\n");
			}
		}
	} else { 
		std::printf("Error - amount must be a number.\n"); // TO-DO: fix error
	}

	return credit;
}

ITEM_RECORD User::Bid() {
	ITEM_RECORD itemRecord;
	std::string duration;

	std::printf("Enter item name: ");
	std::cin >> itemRecord.itemName;
	// std::printf("\nEnter number of days to auction: ");
	// std::cin >> duration;
	std::printf("Enter seller's username: ");
	std::cin >> itemRecord.seller;

	// if(is_number(duration) == false){
	// 	std::printf("\nError - Auction Duration must be a number.");
	// 	itemRecord.duration = -1;
	// }else{
	// 	itemRecord.duration = stoi(duration);
	// }

	return itemRecord;
}

REFUND_RECORD User::Refund() {
	REFUND_RECORD refundRecord; // Initialize new REFUND_RECORD

	// Asking user for inputs to run refund operation
	std::string amount;
	std::printf("Enter buyer's username: ");
	std::cin >> refundRecord.buyer;
	std::cin.clear();
	std::printf("Enter seller's username: ");
	std::cin >> refundRecord.seller;
	std::cin.clear();
	std::printf("Enter refund amount: ");
	std::cin >> amount;

	std::stringstream sstr(amount);
	float f;
	if (sstr >> f) { // TO-DO: fix condition
		float famount = std::stof(amount);
		if (typeid(famount) == typeid(float)) {
			refundRecord.amount = famount;
		}
	} else {
		std::printf("Error - amount must be a number.\n"); // TO-DO: fix error
		refundRecord.buyer.empty();
		refundRecord.seller.empty();
		refundRecord.amount = 0;
	}

	return refundRecord;
}

// Validate if input is a number
bool User::is_number(const std::string& s) {
    std::string::const_iterator it = s.begin();
	// Checking if each char is a digit
    while (it != s.end() && isdigit(*it)) {
		++it;
	} 
    return !s.empty() && it == s.end();
}