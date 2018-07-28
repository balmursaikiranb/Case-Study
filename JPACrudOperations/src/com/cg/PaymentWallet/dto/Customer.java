package com.cg.PaymentWallet.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
@Entity
@Table(name="Paymentwallets")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)	
public class Customer implements Serializable{
@Id
private String phoneNumber;
private String emailId;
private String name;
private String statement;
public String getStatement() {
	return statement;
}
public void setStatement(String statement) {
	this.statement = statement;
}
public Customer(String phoneNumber, String emailId, String name, Integer age, String gender) {
	super();
	this.phoneNumber = phoneNumber;
	this.emailId = emailId;
	this.name = name;
	this.age = age;
	this.gender = gender;
}
public Customer() {
	// TODO Auto-generated constructor stub
}
private Integer age;
private String gender;
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getAge() {
	return age;
}
public void setAge(Integer age) {
	this.age = age;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
}

