package com.example;

class User extends Person implements Authenticatable{
    void login() {
        Database database = new Database();
        database.connect();
    }
    int number() {
        return 7;
    }
}
