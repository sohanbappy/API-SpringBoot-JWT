package com.rokoassessment.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class PatientController {
	
	@Autowired
	private PatientRepo patrepo;

	@GetMapping(path="/api/patients")
	public List<Patient> getAllPatients(){
		return patrepo.findAll();
	}
	@PostMapping(path="/api/insert/patient/new", consumes = {"application/json"})
	public Patient addPatient(@RequestBody Patient pat) {
		patrepo.save(pat);
		return pat;
	}
	@DeleteMapping("/api/delete/patients/{id}")
	public String deletePatient(@PathVariable int pat_id){
		Patient patient = patrepo.getOne(pat_id);
		patrepo.delete(patient);
		return "deleted";
	}
	@PutMapping(path="/api/update/patients", consumes = {"application/json"})
	public String saveOrUpdatePatient(@RequestBody Patient pat){
		patrepo.save(pat);
		return "updated";
	}
	@RequestMapping(path="api/patients/{id}")
	public Optional<Patient> getPatient(@PathVariable int pat_id){
		Optional<Patient> patients = patrepo.findById(pat_id);
		return patients;
	}
}