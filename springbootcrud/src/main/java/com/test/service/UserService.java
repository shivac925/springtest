package com.test.service;

import java.util.List;

import com.test.model.User;

public interface UserService {

	User findById(Long email);
	 
//    User findByName(String name);
 
    void saveUser(User user);
 
    void updateUser(User user);
 
    void deleteUserByEmail(Long email);
 
    void deleteAllUsers();
 
    List<User> findAllUsers();
 
    boolean isUserExist(User user);
    
}
