package com.example.desafiobackend.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafiobackend.entities.Checkin;
import com.example.desafiobackend.entities.Hospede;
import com.example.desafiobackend.repositories.CheckInRepository;
import com.example.desafiobackend.repositories.HospedeRepository;
import com.example.desafiobackend.services.DbService;
@Service
public class DbServiceImpl implements DbService{

	@Autowired
	HospedeRepository hospedeRepository;
	@Autowired
	CheckInRepository checkinRepository;
	
	@Override
	public void popularBancoDeDados() {
	List<Hospede> listaHospede = new ArrayList<Hospede>();
		List<Checkin> listaChekins = new ArrayList<Checkin>();
		for(int i = 1;i <= 10; i++) {
			Hospede novoHospede = new Hospede(null, "Pessoa"+i, "97524793"+i, "1532065"+i);
			listaHospede.add(novoHospede);
			Checkin checkin = new Checkin(null,novoHospede,LocalDateTime.now(),null,false);
			if(i%2==0) {
				checkin.setDataSaida(checkin.getDataEntrada().plusDays((long)(Math.random()*10L)));
			}if(i%3 == 0) {
				checkin.setAdicionalVeiculo(true);
			}
			listaChekins.add(checkin);
		}
		hospedeRepository.saveAll(listaHospede);
		checkinRepository.saveAll(listaChekins);
	}
}
