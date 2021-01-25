package com.masivian.pruebacleancode.dto;

public class BetDTO {

	private Float stake;
	
	private int number;
	
	private Long rouletteId;
	
	private Long userId;

	public BetDTO() {
		
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
