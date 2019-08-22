package com.rokoassessment.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.rokoassessment.dao.DoctorRepo;
import com.rokoassessment.models.Doctor;
import com.rokoassessment.services.DoctorService;

@RestController
public class DoctorController {
	
	@Autowired
	private DoctorRepo docrepo;
	@Autowired
	private DoctorService docServ;

	@GetMapping(path="/api/doctors")
	public List<Doctor> getAllDoctors(){
		return docrepo.findAll();
	}
	@PostMapping(path="/api/insert/doctor/new", consumes = {"application/json"})
	public ResponseEntity<String> addDoctor(@RequestBody Doctor doc) {
		 //validate
		 boolean valid = docServ.isValid(doc);
		 if(valid) {
		 docrepo.save(doc);
		 return new ResponseEntity<String>("created",HttpStatus.OK);
		}else {
			 return new ResponseEntity<String>("not created",HttpStatus.BAD_REQUEST);
		}
		
	}
	@DeleteMapping("/api/delete/doctors/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable(value="id") int doc_id){
		Doctor doctor = docrepo.getOne(doc_id);
		docrepo.delete(doctor);
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	@PutMapping(path="/api/update/doctors/{id}", consumes = {"application/json"})
	public ResponseEntity<?> saveOrUpdateDoctor(@RequestBody Doctor doc,@PathVariable(value="id") int doc_id){
		Doctor doctor = docrepo.getOne(doc_id);
		doctor.setName(doc.getName());
		doctor.setDept(doc.getDept());
		doctor.setJoining(doc.getJoining());
		//validate
		boolean valid = docServ.isValid(doctor);
		if(valid) {
			//setting header
		HttpHeaders header = new HttpHeaders();
		header.add("doctor_id", String.valueOf(doc_id));
		docrepo.save(doctor);
		return ResponseEntity.ok("updated");
		}else {
		return ResponseEntity.badRequest().body("not updated");
		}
		
	}
	@RequestMapping(path="api/doctors/{id}")
	public Optional<Doctor> getDoctor(@PathVariable(value="id") int doc_id){
		Optional<Doctor> doctors = docrepo.findById(doc_id);
		return doctors;
	}
}
