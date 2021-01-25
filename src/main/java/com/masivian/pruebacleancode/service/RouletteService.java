package com.masivian.pruebacleancode.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.masivian.pruebacleancode.dto.GenericResponse;
import com.masivian.pruebacleancode.entity.Roulette;

public interface RouletteService {

	ResponseEntity<Roulette> newRoulette();
	
	ResponseEntity<GenericResponse> openRoulette(Long rouletteId);
	
	ResponseEntity<GenericResponse> closeRoulette(Long rouletteId);
	
	ResponseEntity<GenericResponse> makeBet(Long userId, Float stake, Integer number, Long rouletteId);
	
	ResponseEntity<List<Roulette>> listRoulettes();
}
