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
import com.rokoassessment.dao.DoctorRepo;
import com.rokoassessment.models.Doctor;

@RestController
public class DoctorController {
	
	@Autowired
	private DoctorRepo docrepo;

	@GetMapping(path="/api/doctors")
	public List<Doctor> getAllDoctors(){
		return docrepo.findAll();
	}
	@PostMapping(path="/api/insert/doctor/new", consumes = {"application/json"})
	public Doctor addDoctor(@RequestBody Doctor doc) {
		docrepo.save(doc);
		return doc;
	}
	@DeleteMapping("/api/delete/doctors/{id}")
	public String deleteDoctor(@PathVariable(value="id") int doc_id){
		Doctor doctor = docrepo.getOne(doc_id);
		docrepo.delete(doctor);
		return "deleted";
	}
	@PutMapping(path="/api/update/doctors/{id}", consumes = {"application/json"})
	public String saveOrUpdateDoctor(@RequestBody Doctor doc,@PathVariable(value="id") int doc_id){
		Doctor doctor = docrepo.getOne(doc_id);
		doctor.setName(doc.getName());
		doctor.setDept(doc.getDept());
		doctor.setJoining(doc.getJoining());
		docrepo.save(doctor);
		
		return "updated";
	}
	@RequestMapping(path="api/doctors/{id}")
	public Optional<Doctor> getDoctor(@PathVariable(value="id") int doc_id){
		Optional<Doctor> doctors = docrepo.findById(doc_id);
		return doctors;
	}
}
