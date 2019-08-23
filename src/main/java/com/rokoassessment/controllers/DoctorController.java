package com.rokoassessment.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rokoassessment.dao.DoctorRepo;
import com.rokoassessment.models.Doctor;
import com.rokoassessment.security.JwtGenerator;
import com.rokoassessment.services.DoctorService;

@RestController
public class DoctorController {
	
	@Autowired
	private DoctorRepo docrepo;
	@Autowired
	private DoctorService docServ;
	@Autowired
	JwtGenerator gen;
	Map<String,String> status = new HashMap<String,String>();
	HttpHeaders header = null;
	int doc_id = 0;
	
	@GetMapping(path="/api/doctors")
	public List<Doctor> getAllDoctors(){
		try {
		return docrepo.findAll();
		}catch(Exception ex) {
		ex.printStackTrace();
		return null;	
		}
	}
	@PostMapping(path="/api/insert/doctor/new", consumes = {"application/json"})
	public ResponseEntity<?> addDoctor(@RequestBody Doctor doc) {
		try { 
		//validate
		 boolean valid = docServ.isValid(doc);
		 if(valid) {
		 docrepo.save(doc);
		 //generating token
		 String token = gen.generateDoctorToken(doc);
		 //setting header
		 header = new HttpHeaders();
		 header.add("token",token);
		 status.put("status","success");
		 return ResponseEntity.accepted().headers(header).body(status.entrySet());
		}else {
			status.put("status","not created");
			return ResponseEntity.badRequest().body(status.entrySet());
		}
	  }catch(Exception ex) {
		  ex.printStackTrace();
		  return null;
	  }
		
	}
	@DeleteMapping("/api/delete/doctors")
	public ResponseEntity<?> deleteDoctor(HttpServletRequest req){
		try {
		doc_id = Integer.parseInt(req.getHeader("doctor_id"));
		if(doc_id==0) {
		status.put("status","doctor_id missing in Header");
		return ResponseEntity.badRequest().body(status.entrySet());	
		}
		Doctor doctor = docrepo.getOne(doc_id);
		if(doctor!=null){
		docrepo.delete(doctor);
		//setting header
		 header = new HttpHeaders();
		 header.add("doctor_id",String.valueOf(doc_id));
		 status.put("status","deleted");
		 return ResponseEntity.accepted().headers(header).body(status.entrySet());
		
		}else {
			status.put("status","not found");
			return ResponseEntity.badRequest().body(status.entrySet());
		}
	  }catch(Exception ex) {
		  ex.printStackTrace();
		  return null;
	  }
	}
	@PutMapping(path="/api/update/doctors", consumes = {"application/json"})
	public ResponseEntity<?> saveOrUpdateDoctor(@RequestBody Doctor doc,HttpServletRequest req){
		try {
		doc_id = Integer.parseInt(req.getHeader("doctor_id"));
		if(doc_id==0) {
		status.put("status","doctor_id missing in Header");
		return ResponseEntity.badRequest().body(status.entrySet());	
		}
		Doctor doctor = docrepo.getOne(doc_id);
		doctor.setName(doc.getName());
		doctor.setDept(doc.getDept());
		doctor.setJoining(doc.getJoining());
		//validate
		boolean valid = docServ.isValid(doctor);
		if(valid){
		//generating token
		String token = gen.generateDoctorToken(doc);
		//setting header
		header = new HttpHeaders();
		header.add("token",token);
		header.add("doctor_id", String.valueOf(doc_id));
		docrepo.save(doctor);
		status.put("status","updated");
		return ResponseEntity.accepted().headers(header).body(status.entrySet());
		}else {
		status.put("status","not updated");	
		return ResponseEntity.badRequest().body(status.entrySet());
		}
	 }catch(Exception ex) {
		  ex.printStackTrace();
		  return null;
	  }
		
	}
	@RequestMapping(path="api/doctors")
	public ResponseEntity<?> getDoctor(HttpServletRequest req){
		try {
		doc_id = Integer.parseInt(req.getHeader("doctor_id"));
		if(doc_id==0) {
		status.put("status","doctor_id missing in Header");
		return ResponseEntity.badRequest().body(status.entrySet());	
		}
		Optional<Doctor> doctors = docrepo.findById(doc_id);
		header.add("doctor_id", String.valueOf(doc_id));
		return ResponseEntity.accepted().headers(header).body(doctors);
		}
		catch(Exception ex) {
			  ex.printStackTrace();
			  return null;
		  }
	}
}
