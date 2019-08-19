package com.rokoassessment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.rokoassessment.models.User;

@RepositoryRestResource(collectionResourceRel="Users",path="Users")
public interface UserRepo extends JpaRepository<User,Integer>{

	
}
