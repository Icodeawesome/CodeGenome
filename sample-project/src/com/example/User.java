package com.example;

class User extends Person implements Authenticatable{
    void login() {
        Person person = new Person();
    }
    int number() {
        return 7;
    }
}
