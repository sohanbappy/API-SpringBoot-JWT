package com.rokoassessment.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rokoassessment.dao.UserRepo;
import com.rokoassessment.models.User;

@RestController
public class UserController {

	@Autowired
	UserRepo usrepo;
	
	@GetMapping(path="/users")
	public List<User> getAllUser(){
		return usrepo.findAll();
	}
	@PostMapping(path="/register", consumes = {"application/json"})
	public User addUser(@RequestBody User user) {
		usrepo.save(user);
		return user;
	}
}
