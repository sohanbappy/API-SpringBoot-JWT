package com.rokoassessment.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import com.rokoassessment.models.Doctor;

@Service
public class DoctorService {

	public boolean isValid(Doctor doc) {
		//getting today
		Date	today = new Date();
    	SimpleDateFormat frmt=new SimpleDateFormat("dd-MM-yyyy");
    	frmt.format(today);
    	
    	if(doc.getName()==null || doc.getName().isEmpty()) {
    		return false;
    	}
    	if(doc.getDept()==null || doc.getDept().isEmpty()) {
    		return false;
    	}
    	if(doc.getJoining()==null) {
    		return false;
    	}
    	
    	if (doc.getName().matches("^(([a-z]+)[ ]?)*$")) {
    		if(doc.getJoining().compareTo(today)<0) {
    			return true;
    		}
    		else{
        		return false;
        	}
    	}else {
    		return false;
    	}
    	
		
	}
}
