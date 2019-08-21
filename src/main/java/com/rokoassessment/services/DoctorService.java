package com.rokoassessment.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.rokoassessment.models.Doctor;

@Service
public class DoctorService {

	
	public boolean isValid(Doctor doc) {
		Date joiningDate=null;
		//getting today
		Date today = new Date();
    	SimpleDateFormat frmt=new SimpleDateFormat("dd-MM-yyyy");
    	frmt.format(today);
    	//formatting joining date
		try {
			joiningDate = frmt.parse(doc.getJoining());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	if(doc.getName()==null || doc.getName().isEmpty() || doc.getDept()==null || doc.getDept().isEmpty() || doc.getJoining()==null) {
    		return false;
    	}
    	
    	if (doc.getName().matches("^[a-zA-Z]{1,50}$")) {
    		//joining date 
    		if(joiningDate.compareTo(today)<=0) {
    			return true;
    		}
    		else{
    			System.out.println("date failed");
        		return false;
        	}
    	}else {
    		System.out.println("name failed");
    		return false;
    	}
    	
		
	}
}
