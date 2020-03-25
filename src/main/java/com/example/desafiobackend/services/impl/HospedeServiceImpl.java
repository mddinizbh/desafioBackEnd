package com.example.desafiobackend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.desafiobackend.entities.Hospede;
import com.example.desafiobackend.repositories.HospedeRepository;
import com.example.desafiobackend.services.HospedeService;
import com.example.desafiobackend.services.dtos.HospedeDTO;
import com.example.desafiobackend.services.exceptions.DataIntegrityException;
import com.example.desafiobackend.services.exceptions.SemResultadoException;
import com.example.desafiobackend.utils.mensagens.MensagensHospede;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospedeServiceImpl implements HospedeService {
	private final HospedeRepository hospedeRepository;

	@Override
	public Long salvar(HospedeDTO dto) {
		dto.setId(null);
		Hospede entidade = entidadefromDTO(dto);
		try {
			entidade = hospedeRepository.save(entidade);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(MensagensHospede.ERRO_REGISTRO_EXISTENTE);
		}
		return entidade.getId();
	}

	@Override
	public HospedeDTO alterar(HospedeDTO dto) {
		Hospede entidade = entidadefromDTO(dto);
		try {
			entidade = hospedeRepository.save(entidade);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(MensagensHospede.ERRO_REGISTRO_EXISTENTE);
		}
		return entidadetoDTO(entidade);
	}

	@Override
	public List<HospedeDTO> findAll() {
		List<Hospede> result = hospedeRepository.findAll();
		List<HospedeDTO> resultDto = result.stream().map(obj -> entidadetoDTO(obj)).collect(Collectors.toList());
		return resultDto;
	}
	

	@Override
	public Page<HospedeDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		Page<Hospede> pageHospede = hospedeRepository.findAll(pageRequest);
		return pageHospede.map(HospedeServiceImpl::entidadetoDTO) ;
	}

	@Override
	public Hospede buscarHospede(HospedeDTO dto) {
		try {
			return hospedeRepository.findOneByOptionalParams(dto.getNome(), dto.getTelefone(), dto.getDocumento());
		}catch (NoResultException e) {
			throw new SemResultadoException(MensagensHospede.BUSCA_SEM_RESULTADO);
		}
	}
	
	public static Hospede entidadefromDTO(HospedeDTO dto) {
		return new Hospede(dto.getId(),dto.getNome(),dto.getTelefone(),dto.getDocumento());
	}
	public static HospedeDTO entidadetoDTO(Hospede entidade) {
		return new HospedeDTO(entidade.getId(),entidade.getNome(),entidade.getTelefone(),entidade.getDocumento());
	}

	@Override
	public void deletar(Long id) {
		try {
			hospedeRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(MensagensHospede.ERRO_REGISTRO_ASSOCIADO);
		}
	}
	@Override
	public HospedeDTO findById(Long id) {
		try {
			return entidadetoDTO(hospedeRepository.findById(id).get());
		}catch (ObjectNotFoundException e) {
			throw new SemResultadoException(MensagensHospede.BUSCA_SEM_RESULTADO);
		}
	}

	@Override
	public HospedeDTO buscarHospedeDto(HospedeDTO dto) {
		try {
			Hospede hospede = hospedeRepository.findOneByOptionalParams(dto.getNome(), dto.getTelefone(), dto.getDocumento());
			return entidadetoDTO(hospede);
		}catch (Exception e) {
			throw new SemResultadoException(MensagensHospede.BUSCA_SEM_RESULTADO);
		}
	}
}
