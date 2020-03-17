package com.example.desafiobackend.services;

import java.util.List;

import com.example.desafiobackend.services.dtos.CheckinDTO;

public interface CheckinService {
	
	public List<CheckinDTO> buscarCheckinAtivo();
	
	public List<CheckinDTO> buscarCheckinInativo();
	
	public Long salvar(CheckinDTO dto);
	
	public CheckinDTO alterar(CheckinDTO dto);
	
}
