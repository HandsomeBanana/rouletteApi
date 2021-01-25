package com.masivian.pruebacleancode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.masivian.pruebacleancode.dto.BetDTO;
import com.masivian.pruebacleancode.dto.GenericResponse;
import com.masivian.pruebacleancode.dto.RouletteIdDTO;
import com.masivian.pruebacleancode.entity.Roulette;
import com.masivian.pruebacleancode.entity.User;
import com.masivian.pruebacleancode.repository.UserRepository;
import com.masivian.pruebacleancode.service.RouletteService;

@RestController
@RequestMapping("/rouletteApi")
public class RouletteController {
	
	@Autowired
	RouletteService rouletteService;
	
	@Autowired
	UserRepository urepo;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Roulette> newRoulette() {
		return rouletteService.newRoulette();
	}

	@PostMapping("/createUser")
	@ResponseBody
	public String newUser() {
		User user = new User(5000f);
		user = urepo.save(user);
		return "usuario "+user.getUserId()+" creado";
	}
	
	@PostMapping("/openRoulette")
	public ResponseEntity<GenericResponse> openRoulette(@RequestBody RouletteIdDTO rouletteId) {
		return rouletteService.openRoulette(rouletteId.getRouletteId());
	}

	@PostMapping("/closeRoulette")
	public ResponseEntity<GenericResponse> closeRoulette(@RequestBody RouletteIdDTO rouletteId) {
		return rouletteService.closeRoulette(rouletteId.getRouletteId());
	}

	@PostMapping("/newBet")
	public ResponseEntity<GenericResponse> makeBet(@RequestBody BetDTO bet) {
		return rouletteService.makeBet(bet.getUserId(), bet.getStake(), bet.getNumber(), bet.getRouletteId());
	}

	@GetMapping("/getRoulettes")
	@ResponseBody
	public ResponseEntity<List<Roulette>> listRoulettes() {
		return rouletteService.listRoulettes();
	}

	
	
}
