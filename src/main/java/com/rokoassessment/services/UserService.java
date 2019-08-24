package com.rokoassessment.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.rokoassessment.dao.UserRepo;
import com.rokoassessment.models.User;

@Service
public class UserService {

	UserRepo usrepo;
	public boolean isValid(User user) {
		if(user.getFirst_name()==null || user.getFirst_name().isEmpty() || user.getLast_name()==null || user.getLast_name().isEmpty() || user.getMobile()==null ||
				user.getMobile().isEmpty() || user.getEmail()==null || user.getEmail().isEmpty() || user.getPassword()==null || user.getPassword().isEmpty()) {
			return false;
		}
		if(user.getFirst_name().matches("^[a-zA-Z]{1,50}$") && user.getLast_name().matches("^[a-zA-Z]{1,50}$")) {
			if(user.getEmail().matches("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$")) {
			return true;
			}else {
				System.out.println("email failed");
				return false;
			}
		}else {
			System.out.println("name failed");
			return false;
		}
	}
	public List<User> getUserByEmail(String email) {
		return usrepo.findUserByEmail(email);
	}
}
