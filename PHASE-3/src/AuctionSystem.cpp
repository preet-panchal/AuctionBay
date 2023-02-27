#include "AuctionSystem.h"
#include "User.h"
#include <algorithm>
#include <sstream>

// Initializes the Auction System class constructor
AuctionSystem::AuctionSystem(bool auctionStatus) {
	isOpen = auctionStatus;
	loggedIn = false;
}

bool AuctionSystem::Login(USER_RECORD userRecord, User &currentUser) {
	loggedIn = true; // Login method called once user logins in

	// Initializing all user information from current active login in session
	currentUser.username = userRecord.username;
	currentUser.accountType = userRecord.accountType;
	currentUser.credit = userRecord.credit;

	// Welcome user prompt
	std::cout << "Hello " << currentUser.username << " (" << currentUser.accountType << ")!" << std::endl;
	return loggedIn;
}

// Logout method called once user logs out
void AuctionSystem::Logout(User &currentUser) {
	std::printf("%s successfully logged out.\n", currentUser.username.c_str());
	loggedIn = false;		// Reset loggedIn status once user logsout
}

