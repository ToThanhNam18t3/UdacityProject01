package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

   private final HashService hashService;
   private final UserMapper userMapper;


    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public User getUserByUsername(String username){
        return userMapper.getUserByUsername(username);
    }

    public boolean checkUserAvailable(String username){
        return userMapper.getUserByUsername(username) == null;
    }

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insertUser(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(),
                user.getLastName()));
    }
}
