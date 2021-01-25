package com.masivian.pruebacleancode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masivian.pruebacleancode.entity.Bet;

public interface BetRepository extends JpaRepository<Bet, Long>{

	List<Bet> findByRouletteId(Long rouletteId);
	
}
