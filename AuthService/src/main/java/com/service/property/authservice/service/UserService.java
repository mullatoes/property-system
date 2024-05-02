package com.service.property.authservice.service;

import com.service.property.authservice.model.User;
import com.service.property.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User user) {
        User userToUpdate = userRepository.findById(userId).get();

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());

        return userRepository.save(userToUpdate);
    }

    public User validateUser(User user) {

        User userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername != null) {

            if (userByUsername.getPassword().equals(user.getPassword())) {
                return userByUsername;
            }
        }
        return null;
    }

    public User getUserByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }
}
