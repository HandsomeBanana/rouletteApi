package com.masivian.pruebacleancode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="bet")
public class Bet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long betId;
	
	@Column
	private Float stake;
	
	@Column
	private int number;
	
	@Column
	private Long rouletteId;
	
	@Column
	private Long userId;

	public Bet() {
		
	}
	
	
	
	public Bet(Float stake, int number, Long rouletteId, Long userId) {
		super();
		this.stake = stake;
		this.number = number;
		this.rouletteId = rouletteId;
		this.userId = userId;
	}



	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public Float getStake() {
		return stake;
	}

	public void setStake(Float stake) {
		this.stake = stake;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Long getRouletteId() {
		return rouletteId;
	}

	public void setRouletteId(Long rouletteId) {
		this.rouletteId = rouletteId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
