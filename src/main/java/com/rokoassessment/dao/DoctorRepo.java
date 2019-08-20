package com.rokoassessment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.rokoassessment.models.Doctor;

@RepositoryRestResource(collectionResourceRel="Doctors",path="Doctors")
public interface DoctorRepo extends JpaRepository<Doctor,Integer>{

	
}
