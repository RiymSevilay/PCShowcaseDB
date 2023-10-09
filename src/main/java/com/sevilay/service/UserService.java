package com.sevilay.service;

import com.sevilay.repository.UserRepository;
import com.sevilay.repository.entity.User;

import java.util.Optional;

public class UserService {

    UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> usernameGoreKullaniciBul(String username) {
        return userRepository.usernameGoreKullaniciBul(username);
    }


    public Optional<User> findById(Long userid) {
        return userRepository.findById(userid);
    }
}
