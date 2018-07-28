package com.cg.paymentwallet.dto;

import java.math.BigDecimal;

public class Wallet extends Customer{
private BigDecimal balance;
private String password;
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
Customer details;
	
	@Override
public String toString() {
	return "Wallet [balance=" + balance + ", password=" + password + ", details=" + details + "]";
}
	public Wallet(String phoneNumber, String emailId, String name, Integer age, String gender, BigDecimal balance,
		String password, Customer details) {
	super(phoneNumber, emailId, name, age, gender);
	this.balance = balance;
	this.password = password;
	this.details = details;
}
	public Wallet() {
		// TODO Auto-generated constructor stub
		super();
	}
	public Customer getDetails() {
		return details;
	}
	public void setDetails(Customer details) {
		this.details = details;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}

