#include <string>
#include <iostream>
#include <sstream>
#include <type_traits>
#include <typeinfo>
using namespace std;


int main () {

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

    string user_input;
    do {
        printf("Enter operation: ");
        cin >> user_input;
        printf("\ntest\n");
    } while (user_input != "logout");
};

