package com.rokoassessment.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rokoassessment.dao.UserRepo;
import com.rokoassessment.models.User;
import com.rokoassessment.services.UserService;
import com.rokoassessment.security.JwtGenerator;


@RestController
public class UserController {

	@Autowired
	UserRepo usrepo;
	@Autowired
	UserService usServ;
	@Autowired
	JwtGenerator gen;
	Map<String,String> status = new HashMap<String,String>(); 
	
	@GetMapping(path="/users")
	public List<User> getAllUser(){
		return usrepo.findAll();
	}
	@PostMapping(path="/register", consumes = {"application/json"})
	public ResponseEntity<?> addUser(@RequestBody User user) {
		//validate 
		boolean valid = usServ.isValid(user);
		if(valid) {
		usrepo.save(user);
		//generating token
		String token = gen.generate(user);
		//setting header
		HttpHeaders header = new HttpHeaders();
		header.add("token",token);
		return ResponseEntity.accepted().headers(header).body(user);
		}else {
		status.put("status","not created");
		return ResponseEntity.badRequest().body(status.entrySet());
		}
	}
	@PostMapping(path="/login", consumes = {"application/json"})
	public User loginUser(@RequestBody String email,@RequestBody String password) {
		User user=null;
		return user;
	}
	
}
