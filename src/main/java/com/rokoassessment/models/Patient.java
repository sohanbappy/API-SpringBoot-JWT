package com.rokoassessment.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="patient_tb")
public class Patient {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@Column(name="name",nullable=false)
	private String name;
	@Column(name="mobile",nullable=false)
	private String mobile;
	@Column(name="age",nullable=false)
	@Size(min=1,max=3)
	private int age;
	@Column(name="gender",nullable=false)
	private String gender;
	@Column(name="occupation",nullable=false)
	private String occupation;
	@Column(name="symptom_summary")
	private String symptom_summary;
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getSymptom_summary() {
		return symptom_summary;
	}
	public void setSymptom_summary(String symptom_summary) {
		this.symptom_summary = symptom_summary;
	}
	public Patient(int id, String name, String mobile, @Size(min = 1, max = 3) int age, String gender,
			String occupation, String symptom_summary) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		this.symptom_summary = symptom_summary;
	}
	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", mobile=" + mobile + ", age=" + age + ", gender=" + gender
				+ ", occupation=" + occupation + ", symptom_summary=" + symptom_summary + "]";
	}
	

}
