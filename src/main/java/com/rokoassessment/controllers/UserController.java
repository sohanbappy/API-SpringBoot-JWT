package com.rokoassessment.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rokoassessment.dao.UserRepo;
import com.rokoassessment.models.JwtUserDetails;
import com.rokoassessment.models.JwtUserLogin;
import com.rokoassessment.models.User;
import com.rokoassessment.services.UserService;
import com.rokoassessment.security.JwtAuthenticationProvider;
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
    EntityManager entityManager;
    @Autowired
    JwtAuthenticationProvider authProvider;
    
	@GetMapping(path="/users")
	public List<User> getAllUser(){
		try {
		return usrepo.findAll();
		}catch(Exception ex) {
			  ex.printStackTrace();
			  return null;
		  }
	}
	@PostMapping(path="/register", consumes = {"application/json"})
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
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
	  }catch(Exception ex) {
		  ex.printStackTrace();
		  return null;
	  }
	}

	@PostMapping(path="/login", consumes = {"application/json"})
	public ResponseEntity<?> userLogin(@RequestBody JwtUserLogin user,HttpServletRequest req) {
		try {
		String token = req.getHeader("token");
		JwtUserDetails jwtUser = authProvider.retrieveUserNameAndEmail(user,token);
		//getting User info if needed
		List<User> users = usServ.getUserByEmail(jwtUser.getEmail());
		//setting header
		HttpHeaders header = new HttpHeaders();
		header.add("token",token);
		status.put("status", "logged in");
		status.put("first_name", jwtUser.getFirst_name());
		status.put("email",jwtUser.getEmail());
		return ResponseEntity.accepted().headers(header).body(status.entrySet());
		}catch(Exception ex) {
			  ex.printStackTrace();
			  return null;
		  }
	}

}
