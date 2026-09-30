package com.example.service;

import com.example.repo.UserRepository;

public class UserService {

    private UserRepository repository = new UserRepository();

    public void register(UserRepository param) {
        repository.save();
        param.save();
        System.out.println("x");
    }
}
