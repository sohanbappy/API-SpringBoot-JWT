package com.rokoassessment.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rokoassessment.dao.PatientRepo;
import com.rokoassessment.models.Patient;
import com.rokoassessment.services.PatientService;

@RestController
public class PatientController {
	
	@Autowired
	private PatientRepo patrepo;
	@Autowired
	private PatientService patServ; 

	@GetMapping(path="/api/patients")
	public List<Patient> getAllPatients(){
		return patrepo.findAll();
	}
	@PostMapping(path="/api/insert/patient/new", consumes = {"application/json"})
	public ResponseEntity<String> addPatient(@RequestBody Patient pat) {
		//validate
		boolean valid = patServ.isValid(pat);
		if(valid) {
		patrepo.save(pat);
		return new ResponseEntity<String>("created",HttpStatus.OK);
		}else {
		return new ResponseEntity<String>("not created",HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping("/api/delete/patients/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable(value="id") int pat_id){
		Patient patient = patrepo.getOne(pat_id);
		patrepo.delete(patient);
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	@PutMapping(path="/api/update/patients/{id}", consumes = {"application/json"})
	public ResponseEntity<String> saveOrUpdatePatient(@RequestBody Patient pat,@PathVariable(value="id") int pat_id){
		Patient patient = patrepo.getOne(pat_id);
		patient.setName(pat.getName());
		patient.setAge(pat.getAge());
		patient.setGender(pat.getGender());
		patient.setMobile(pat.getMobile());
		patient.setOccupation(pat.getOccupation());
		patient.setSymptom_summary(pat.getSymptom_summary());
		//validate
		boolean valid = patServ.isValid(pat);
		if(valid) {
		patrepo.save(patient);
		return new ResponseEntity<String>("updated",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("not updated",HttpStatus.BAD_REQUEST);	
		}
		
	}
	@RequestMapping(path="api/patients/{id}")
	public Optional<Patient> getPatient(@PathVariable(value="id") int pat_id){
		Optional<Patient> patients = patrepo.findById(pat_id);
		return patients;
	}
}
