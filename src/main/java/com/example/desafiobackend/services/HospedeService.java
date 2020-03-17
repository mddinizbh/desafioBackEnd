package com.example.desafiobackend.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.desafiobackend.entities.Hospede;
import com.example.desafiobackend.services.dtos.HospedeDTO;

public interface HospedeService {
	
	
	public HospedeDTO alterar(HospedeDTO dto);
	
	public List<HospedeDTO> findAll();
	
	public Page<HospedeDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);
	
	public Hospede buscarHospede(HospedeDTO dto);
	
	public Long salvar(HospedeDTO dto);
	
	public void deletar(Long id);
   
	public HospedeDTO findById(Long id);
}
