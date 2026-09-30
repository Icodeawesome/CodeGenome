package com.example;

class User extends Person implements Authenticatable{
    void login() {
        Database database = new Database();
        database.connect();
        System.out.println("Logging in");
    }
    int number() {
        return 7;
    }
}
