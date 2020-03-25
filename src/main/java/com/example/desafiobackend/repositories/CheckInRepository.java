package com.example.desafiobackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiobackend.entities.Checkin;

public interface CheckInRepository extends JpaRepository<Checkin, Long> {
	
	public List<Checkin>  findByDataSaidaNotNull();
	
	public List<Checkin>  findByDataSaidaIsNull();
	
	
}
