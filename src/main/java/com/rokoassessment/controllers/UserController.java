package com.rokoassessment.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rokoassessment.dao.UserRepo;
import com.rokoassessment.models.User;
import com.rokoassessment.services.UserService;

@RestController
public class UserController {

	@Autowired
	UserRepo usrepo;
	@Autowired
	UserService usServ;
	
	@GetMapping(path="/users")
	public List<User> getAllUser(){
		return usrepo.findAll();
	}
	@PostMapping(path="/register", consumes = {"application/json"})
	public User addUser(@RequestBody User user) {
		//validate 
		boolean valid = usServ.isValid(user);
		if(valid) {
		usrepo.save(user);
		}
		return user;
	}
	@PostMapping(path="/login", consumes = {"application/json"})
	public User loginUser(@RequestBody String email,@RequestBody String password) {
		User user=null;
		return user;
	}
	
}
