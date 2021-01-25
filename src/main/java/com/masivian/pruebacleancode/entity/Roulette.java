package com.masivian.pruebacleancode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.masivian.pruebacleancode.util.RouletteStatus;

@Entity(name="roulette")
public class Roulette {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long rouletteId;
	
	@Column
	private RouletteStatus status;

	public Roulette() {
		this.status = RouletteStatus.CLOSED;
	}

	public Long getRouletteId() {
		return rouletteId;
	}

	public void setRouletteId(Long rouletteId) {
		this.rouletteId = rouletteId;
	}

	public RouletteStatus getStatus() {
		return status;
	}
	
	public void open() {
		this.status = RouletteStatus.OPEN;
	}
	
	public void close() {
		this.status = RouletteStatus.CLOSED;
	}
	
}
