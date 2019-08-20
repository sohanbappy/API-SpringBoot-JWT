package com.rokoassessment.models;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="doctor_tb")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@Column(name="name",nullable=false)
	private String name;
	@Column(name="dept",nullable=false)
	private String dept;
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name="joining",nullable=false)
	private Date joining;
	@Autowired
	@ManyToMany(mappedBy="doctors")
	private List<Patient> patients;
	
	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getJoining() {
		return joining;
	}

	public void setJoining(Date joining) {
		this.joining = joining;
	}

	public Doctor(int id, String name, Date joining) {
		super();
		this.id = id;
		this.name = name;
		this.joining = joining;
	}

	public Doctor() {
		super();
	}

	

}
