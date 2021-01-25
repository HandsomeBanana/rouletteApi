package com.masivian.pruebacleancode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userId;
	
	@Column
	private Float balance;
	
	public User() {
		
	}

	
	public User(Float balance) {
		this.balance = balance;
	}
	
	public User(Long userId, Float balance) {
		super();
		this.userId = userId;
		this.balance = balance;
	}



	public Long getUserId() {
		return userId;
	}

	public void setuserId(Long userId) {
		this.userId = userId;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}
	
	
}
