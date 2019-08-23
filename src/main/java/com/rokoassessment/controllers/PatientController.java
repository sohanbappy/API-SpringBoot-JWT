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
import com.rokoassessment.dao.PatientRepo;
import com.rokoassessment.models.Patient;
import com.rokoassessment.security.JwtGenerator;
import com.rokoassessment.services.PatientService;

@RestController
public class PatientController {
	
	@Autowired
	private PatientRepo patrepo;
	@Autowired
	private PatientService patServ; 
	JwtGenerator gen;
	Map<String,String> status = new HashMap<String,String>();
	HttpServletRequest req = null;
	HttpHeaders header = null;
	int pat_id = 0;
	
	@GetMapping(path="/api/patients")
	public List<Patient> getAllPatients(){
		return patrepo.findAll();
	}
	@PostMapping(path="/api/insert/patient/new", consumes = {"application/json"})
	public ResponseEntity<?> addPatient(@RequestBody Patient pat) {
		//validate
		boolean valid = patServ.isValid(pat);
		if(valid) {
		patrepo.save(pat);
		//generating token
		 String token = gen.generatePatientToken(pat);
		 //setting header
		 header = new HttpHeaders();
		 header.add("token",token);
		 status.put("status","success");
		 return ResponseEntity.accepted().headers(header).body(status.entrySet());
		}else {
			status.put("status","not created");
			return ResponseEntity.badRequest().body(status.entrySet());
		}
	}
	@DeleteMapping("/api/delete/patients")
	public ResponseEntity<?> deletePatient(){
		pat_id = Integer.parseInt(req.getHeader("patient_id"));
		if(pat_id==0) {
		status.put("status","patient_id missing in Header");
		return ResponseEntity.badRequest().body(status.entrySet());	
		}
		Patient patient = patrepo.getOne(pat_id);
		if(patient!=null){
		patrepo.delete(patient);
		//setting header
		 header = new HttpHeaders();
		 header.add("patient_id",String.valueOf(pat_id));
		 status.put("status","deleted");
		 return ResponseEntity.accepted().headers(header).body(status.entrySet());
		
		}else {
			status.put("status","not found");
			return ResponseEntity.badRequest().body(status.entrySet());
		}
	}
	@PutMapping(path="/api/update/patients", consumes = {"application/json"})
	public ResponseEntity<?> saveOrUpdatePatient(@RequestBody Patient pat){
		pat_id = Integer.parseInt(req.getHeader("patient_id"));
		if(pat_id==0) {
		status.put("status","patient_id missing in Header");
		return ResponseEntity.badRequest().body(status.entrySet());	
		}
		Patient patient = patrepo.getOne(pat_id);
		
		patient.setName(pat.getName());
		patient.setAge(pat.getAge());
		patient.setGender(pat.getGender());
		patient.setMobile(pat.getMobile());
		patient.setOccupation(pat.getOccupation());
		patient.setSymptom_summary(pat.getSymptom_summary());
		//validate
		boolean valid = patServ.isValid(pat);
		if(valid){
		//generating token
		String token = gen.generatePatientToken(pat);
		//setting header
		header = new HttpHeaders();
		header.add("token",token);
		header.add("patient_id", String.valueOf(pat_id));
		patrepo.save(patient);
		status.put("status","updated");
		return ResponseEntity.accepted().headers(header).body(status.entrySet());
			}else {
			status.put("status","not updated");	
			return ResponseEntity.badRequest().body(status.entrySet());
			}
		
	}
	@RequestMapping(path="api/patients")
	public ResponseEntity<?> getPatient(){
		pat_id = Integer.parseInt(req.getHeader("patient_id"));
		if(pat_id==0) {
		status.put("status","patient_id missing in Header");
		return ResponseEntity.badRequest().body(status.entrySet());	
		}
		Optional<Patient> patients = patrepo.findById(pat_id);
		header.add("patient_id", String.valueOf(pat_id));
		return ResponseEntity.accepted().headers(header).body(patients);
	}
}
