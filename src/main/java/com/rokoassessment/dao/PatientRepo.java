package com.rokoassessment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.rokoassessment.models.Patient;

@RepositoryRestResource(collectionResourceRel="Patients",path="Patients")
public interface PatientRepo extends JpaRepository<Patient,Integer>{

	
}
