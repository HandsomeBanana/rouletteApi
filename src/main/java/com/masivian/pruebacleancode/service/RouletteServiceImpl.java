package com.masivian.pruebacleancode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.masivian.pruebacleancode.dto.GenericResponse;
import com.masivian.pruebacleancode.entity.Bet;
import com.masivian.pruebacleancode.entity.Roulette;
import com.masivian.pruebacleancode.entity.User;
import com.masivian.pruebacleancode.exception.BetException;
import com.masivian.pruebacleancode.repository.BetRepository;
import com.masivian.pruebacleancode.repository.RouletteRepository;
import com.masivian.pruebacleancode.repository.UserRepository;
import com.masivian.pruebacleancode.util.RouletteStatus;

@Component
public class RouletteServiceImpl implements RouletteService{

	@Autowired
	RouletteRepository rouletteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BetRepository betRepository;
	
	@Override
	public ResponseEntity<Roulette> newRoulette() {
		return new ResponseEntity<>(rouletteRepository.save(new Roulette()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<GenericResponse> openRoulette(Long rouletteId) {
		Roulette roulette = rouletteRepository.findById(rouletteId).get();
		if(roulette == null) {
			
			return generateGenericResponse("Acci\u00f3n fallida. No existe la ruleta especificada." , HttpStatus.BAD_REQUEST);
		}else {
			if(roulette.getStatus().equals(RouletteStatus.OPEN)) {
				
				return generateGenericResponse("Acci\u00f3n fallida. La ruleta con id "+rouletteId+" ya se encuentra abierta." , HttpStatus.BAD_REQUEST);
			}
			roulette.open();
			rouletteRepository.save(roulette);
			
			return generateGenericResponse("La ruleta identificada con el id "+rouletteId+" fue abierta exitosamente.", HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<GenericResponse> closeRoulette(Long rouletteId) {
		if(!rouletteRepository.findById(rouletteId).isPresent()) {
			return generateGenericResponse("La ruleta especificada no existe.", HttpStatus.BAD_REQUEST);
		}
		Roulette roulette = rouletteRepository.findById(rouletteId).get();
		if(roulette.getStatus().equals(RouletteStatus.CLOSED)) {
			
			return generateGenericResponse("La ruleta especificada ya se encuentra cerrada.", HttpStatus.BAD_REQUEST);
		}
		int winningNumber = generateWinningNumber();
		List<Bet> rouletteBets = betRepository.findByRouletteId(rouletteId);
		List<Bet> winningBets = getWinningBets(rouletteBets, winningNumber);
		for(Bet bet : winningBets) {
			User user = userRepository.findById(bet.getUserId()).get();
			user.setBalance(user.getBalance()+bet.getStake()*getMultiplier(bet.getNumber(), winningNumber));
			userRepository.save(user);
		}
		roulette.close();
		rouletteRepository.save(roulette);
		
		return generateGenericResponse("La ruleta fue cerrada exitosamente", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<GenericResponse> makeBet(Long userId, Float stake, Integer number, Long rouletteId) {
		try {
			validateBet(userId, stake, number, rouletteId);
		}catch (BetException e) {
			return generateGenericResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		Bet newBet = new Bet(stake, number, rouletteId, userId);
		User user = userRepository.findById(userId).get();
		user.setBalance(user.getBalance() - stake);
		betRepository.save(newBet);
		
		return generateGenericResponse("Apuesta registrada exitosamente", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Roulette>> listRoulettes() {
		return new ResponseEntity<List<Roulette>>(rouletteRepository.findAll(), HttpStatus.OK);
	}

	private ResponseEntity<GenericResponse> generateGenericResponse(String message, HttpStatus status){
		return new ResponseEntity<GenericResponse>(new GenericResponse(message) , status);
	}
	
	private void validateBet(Long userId, Float stake, Integer number, Long rouletteId) throws BetException{
		if(number == null) {
			throw new BetException("Debe ingresar un n\u00famero al cual apostar.");
		}
		if(number < 0 || number > 36) {
			throw new BetException("Ingrese un n\u00famero v\u00e1lido. Recuerde que el n\u00famero debe estar en el rango [0, 36].");
		}
		if(stake == null) {
			throw new BetException("Debe ingresar una cantidad de dinero para apostar");
		}
		if(rouletteId == null) {
			throw new BetException("Debe indicar una ruleta para realizar una apuesta");
		}
		if(userId == null) {
			throw new BetException("Debe indicar el usuario que va a realizar la apuesta.");
		}
		if(!rouletteRepository.findById(rouletteId).isPresent()) {
			throw new BetException("No existe la ruleta especificada.");
		}
		if(rouletteRepository.findById(rouletteId).get().getStatus().equals(RouletteStatus.CLOSED)) {
			throw new BetException("La ruleta sobre la que trata de apostar se encuentra cerrada. No se puede realizar la apuesta");
		}
		if(!userRepository.findById(userId).isPresent()) {
			throw new BetException("No existe el usuario especificado.");
		}
		if(userRepository.findById(userId).get().getBalance() < stake) {
			if(stake> 10000) {
				throw new BetException("Ingrese una cantidad de apuesta v\u00e1lida. Recuerde que la cantidad m\u00e1xima de apuesta es de $10.000 .");
			} else {
				throw new BetException("El usuario no cuenta con la cantidad de dinero especificada para apostar.");
			}
		}
		
	}
	
	private int generateWinningNumber() {
		Random r = new Random();
		int low = 0;
		int high = 36;
		return r.nextInt(high-low) + low;
	}
	
	private List<Bet> getWinningBets(List<Bet> bets, int winningNumber){
		List<Bet> winningBets = new ArrayList<>();
		for(Bet bet : bets) {
			if(bet.getNumber() == winningNumber) {
				winningBets.add(bet);
			}else if(bet.getNumber() % 2 == 0 && winningNumber % 2 == 0) {
				winningBets.add(bet);
			} else if(bet.getNumber() % 2 != 0 && winningNumber % 2 != 0) {
				winningBets.add(bet);
			}
		}
		
		return winningBets;
	}
	
	private Float getMultiplier(int selectedNumber, int winningNumber) {
		if(selectedNumber == winningNumber) {
			
			return 5f;
		} else if(selectedNumber % 2 == 0 && winningNumber % 2 == 0) {
			
			return 1.8f;
		} else if(selectedNumber % 2 != 0 && winningNumber % 2 != 0) {
			
			return 1.8f;
		} else {
			
			return 0f;
		}
	}
}
