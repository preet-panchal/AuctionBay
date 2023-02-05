
#include <string>
#include <iostream>
#include <sstream>
#include <type_traits>
#include <typeinfo>

using namespace std;

class User {
    public: 
        User(string userName, string userType, double userCredit);
        ~User();
        string toString();

        void setUserName(string userName) {
            this->userName = userName;
        };
        string getUserName() {return userName;};
        
        void setUserType(string userType) {
            this->userType = userType;
        };
        string getUserType();

        void setUserCredit(double userCredit) {
            this->userCredit = userCredit;
        };
        string getUserCredit();


    private:
        string userName;
        string userType; 
        double userCredit;
};

// defining our constructor function
User::User(string userName, string userType, double userCredit) {
    this->userName = userName;
    this->userType = userType;
    this->userCredit = userCredit;
};

// defining our destructor function
User::~User() {
    //cout << "Destructor Called!" <<endl; 
}




