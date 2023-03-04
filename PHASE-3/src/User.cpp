#include "User.h"
#include "AuctionSystem.h"
#include <string>
#include <iostream>
#include <sstream>
#include <type_traits>
#include <typeinfo>

// Returns current user information as a string
std::string User::toString(){
	std::stringstream userStream;
	userStream << username << " " << accountType << " " << credit << " " << password;
	std::string userInfo = userStream.str();
	return userInfo;
}

USER_RECORD User::CreateAccount() {
	bool userExists = false;	// Used to check if user already exists
	USER_RECORD newUser;		// Initialize new USER_RECORD for newly created user

	std::printf("Enter a username to create: ");
	std::cin >> newUser.username;
	if (newUser.username.length() > 15) {
		newUser.accountType = "ER";
		std::cin.ignore();
		return newUser;
	}

	std::printf("Enter an user type: ");
	std::cin >> newUser.accountType;
	if (newUser.accountType != "AA" && newUser.accountType != "FS" && newUser.accountType != "BS" && newUser.accountType != "SS") {
		newUser.accountType = "ER";
		std::cin.ignore();
		return newUser;
	}

	std:string password;
	std::printf("Enter a password: ");
	std::cin >> password;
	std::cin.ignore();
	if (password.length() > 15 || password.length() < 1) {
		printf("Error: Password must be between 1-15 characters long.\n");
	} else {
		newUser.password = Encrypt(password);
	}

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

	// User input for item name for advertise operation
	std::printf("Enter the item name: ");
	std::cin >> itemRecord.itemName;  
	// Checking if item name is valid
	if (itemRecord.itemName.length() > 25 || itemRecord.itemName.length() < 1) {
		itemRecord.duration = 999;
		std::printf("Error: Item name must be between 1-25 characters in length.\n");
		return itemRecord;
	}

	std::cin.clear();

	// User input for minimum bid for advertise operation
	std::printf("Enter the minimum bid ($): ");
	std::cin >> minBid;
	if (is_number(minBid) == false) { // Checking if min bid amount is a number
		itemRecord.duration = 999;
		std::printf("Error: Minimum bid amount must be a number.\n");
		return itemRecord; 
	} else { // Checking if min bid amount is valid
		if (std::stoi(minBid) > 999.99 || std::stoi(minBid) < 0) { 
			itemRecord.duration = 999;
			std::printf("Error: Minimum bid amount must be betwceen 0-999.99.\n");
			return itemRecord;
		} else {
			itemRecord.minBid = std::stoi(minBid);
		}
	}
	
	std::cin.clear();

	// User input for duration for advertise operation
	std::printf("Enter auction duration (in days): ");
	std::cin >> duration;
	if (is_number(duration) == false) { // Checking if duration is a number
		itemRecord.duration = 999;
		std::printf("Error: Duration must be a number.\n");
		return itemRecord; 
	} else { // Checking if duration is valid
		if (std::stoi(duration) > 100 || std::stoi(duration) < 1) { 
			itemRecord.duration = 999;
			std::printf("Error: Auction duration must be between 1-100 days.\n");
			return itemRecord;
		} else {
			itemRecord.duration = std::stoi(duration);
		}
	}

	return itemRecord;
}

/*
ITEM_RECORD User::Bid(std::string itemName, std::string seller) {
	ITEM_RECORD itemRecord;
	itemRecord.itemName = itemName;
	itemRecord.seller = seller;
	return itemRecord;
}
*/

REFUND_RECORD User::Refund() {
	REFUND_RECORD refundRecord; // Initialize new REFUND_RECORD

	// Asking user for inputs to run refund operation
	std::string amount;
	std::printf("Enter refund amount: ");
	std::cin >> amount; 
	if (isFloat(amount)) {
		if (stof(amount) <= 0) {
			printf("Error: Refund amount must be a greater than 0.\n");
			refundRecord.buyer.empty();
			refundRecord.seller.empty();
			refundRecord.amount = 0;
		} else {
			refundRecord.amount = stof(amount);
		}
	} else {
		printf("Error: Refund amount must be a number.\n");
		refundRecord.buyer.empty();
		refundRecord.seller.empty();
		refundRecord.amount = 0;
	}

	return refundRecord;
}

// TO-DO: addcredit does not function as intended
float User::AddCredit(float amount) {
	if ((this->credit + amount) < MAX_CREDIT) {
		this->credit += amount;
		// std::cout << "\nSuccessfully added $ " << amount << std::endl;
		// std::cout << "\nCredit is now: " << credit << std::endl;
	} else {
		std::printf("Error: Exceeded $%i credit limit for this user.\n", MAX_CREDIT); 
	}
	return this->credit;
	/*
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
	*/
}

std::string User::ResetPassword() {
	std:string password;
	std::printf("Enter a password: ");
	std::cin >> password;
	std::cin.ignore();
	if (password.length() > 15 || password.length() < 1) {
		printf("Error: Password must be between 1-15 characters long.\n");
		password = password.empty();
	} else {
		password = Encrypt(password);
		this->password = password;
		printf("Successfully changed password!\n");
	}

	return password;
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

bool User::isFloat(const std::string& s) {
    std::istringstream iss(s);
    float f;
    iss >> std::noskipws >> f;
    return iss.eof() && !iss.fail(); 
}
