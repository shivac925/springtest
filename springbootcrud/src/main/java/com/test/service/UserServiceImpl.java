package com.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.model.User;
import com.test.repositories.UserRepository;
 
 
 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserRepository userRepository;
 
    public User findById(Long id) {
    	Optional<User> oUser = userRepository.findById(id);
    	return oUser.isPresent() ? oUser.get() : null;
    }
 
   /* public User findByName(String name) {
        return userRepository.findByName(name);
    }*/
 
    public void saveUser(User user) {
        userRepository.save(user);
    }
 
    public void updateUser(User user){
        saveUser(user);
    }
 
    public void deleteUserById(User user){
        userRepository.delete(user);
    }
 
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
 
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
 
    public boolean isUserExist(User user) {
    	Optional<User> oUser = userRepository.findById(user.getId());
    	return oUser.isPresent();
    }

	@Override
	public void deleteUserByEmail(Long id) {
		userRepository.deleteById(id);
		
	}
 
}