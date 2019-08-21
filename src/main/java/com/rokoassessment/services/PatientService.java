package com.rokoassessment.services;

import org.springframework.stereotype.Service;
import com.rokoassessment.models.Patient;

@Service
public class PatientService {

	public boolean isValid(Patient pat) {
		if(pat.getName()==null || pat.getName().isEmpty() || pat.getMobile()==null || pat.getMobile().isEmpty() || pat.getAge()<=0 ||
				pat.getGender()==null || pat.getGender().isEmpty() || pat.getOccupation()==null || pat.getOccupation().isEmpty()) {
			return false;
		}
		if(pat.getName().matches("^(([a-z]+)[ ]?)*$")) {
			return true;
		}else {
			return false;
		}
	}
}
