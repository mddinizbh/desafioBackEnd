package com.example.desafiobackend.services.impl;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.desafiobackend.entities.Checkin;
import com.example.desafiobackend.entities.Hospede;
import com.example.desafiobackend.repositories.CheckInRepository;
import com.example.desafiobackend.services.CheckinService;
import com.example.desafiobackend.services.HospedeService;
import com.example.desafiobackend.services.dtos.CheckinDTO;
import com.example.desafiobackend.services.dtos.HospedeDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckinServiceImpl implements CheckinService {

	private final HospedeService hospedeService;
	private final CheckInRepository checkinRepository;
	private static final Long DIARIA_FDS = 150L;
	private static final Long DIARIA = 120L;
	private static final Long TAXA_VEICULO_FDS = 20L;
	private static final Long TAXA_VEICULO = 15L;
	
	@Override
	public List<CheckinDTO> buscarCheckinAtivo() {
		List<Checkin> result = checkinRepository.findByDataSaidaIsNull();
		
		return result.stream().map(obj -> entidadeToDtoComTotal(obj,true)).collect(Collectors.toList());
	}
	@Override
	public List<CheckinDTO> buscarCheckinInativo() {
		List<Checkin> result = checkinRepository.findByDataSaidaNotNull();
		return result.stream().map(obj -> entidadeToDtoComTotal(obj,false)).collect(Collectors.toList());
	}
	
	private Boolean fimDeSemana(LocalDateTime data) {
		if (data.getDayOfWeek() == DayOfWeek.SATURDAY || data.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return true;
		}
		return false;
	}
	
	private Long calcularValorDiaria(Boolean fimDeSemana, Boolean adicionalVeiculo) {
		if(fimDeSemana && adicionalVeiculo) {
			return DIARIA_FDS+TAXA_VEICULO_FDS;
		}else if(!fimDeSemana && adicionalVeiculo) {
			return DIARIA+TAXA_VEICULO;
		}else if (fimDeSemana && !adicionalVeiculo){
			return DIARIA_FDS;
		}else {
			return DIARIA;
		}
	}
	
	private Long calcularGastoTotal(LocalDateTime dataEntrada, LocalDateTime dataSaida, Boolean adicionalVeiculo){
		Long diferencaEmDias = ChronoUnit.DAYS.between(dataEntrada, dataSaida);
		Long valorTotalGasto = 0L;
		for(Long i = 0L; i <= diferencaEmDias; i++){
			valorTotalGasto += calcularValorDiaria(fimDeSemana(dataEntrada.plusDays(i)), adicionalVeiculo); 
		}
		LocalTime localTime = LocalTime.parse("16:30:00");
		
		Long diferencaEmsegundos = ChronoUnit.SECONDS.between(localTime, dataSaida.toLocalTime());
		if(diferencaEmsegundos > 1) {
			valorTotalGasto += calcularValorDiaria(fimDeSemana(dataSaida),adicionalVeiculo);
		}
		return valorTotalGasto;
	}
	private CheckinDTO entidadeToDtoComTotal(Checkin checkin, Boolean ativos) {
		if(checkin.getDataSaida()== null) {
			checkin.setDataSaida(LocalDateTime.now());
		}
		CheckinDTO dto = entidadetoDTO(checkin);
		dto.setUltimaHospedagem(calcularValorDiaria(fimDeSemana(checkin.getDataSaida()), checkin.getAdicionalVeiculo()));
		dto.setValorTotalGasto(calcularGastoTotal(checkin.getDataEntrada(), checkin.getDataSaida(), checkin.getAdicionalVeiculo()));
		if(ativos) {
			dto.setDataSaida("");
		}
		return dto;
	}
	@Override
	public Long salvar(CheckinDTO dto) {
		Hospede hospede = hospedeService.buscarHospede(dto.getHospede());
		Checkin checkin = entidadefromDTO(dto);
		checkin.setHospede(hospede);
		checkin = checkinRepository.save(checkin);
		return checkin.getId();
	}
	@Override
	public CheckinDTO alterar(CheckinDTO dto) {
		Checkin checkin = entidadefromDTO(dto);
		checkin = checkinRepository.save(checkin);
		return entidadetoDTO(checkin);
	}
		
	private Checkin entidadefromDTO(CheckinDTO dto) {
		Hospede hospede = HospedeServiceImpl.entidadefromDTO(dto.getHospede());
		return new 	Checkin(dto.getId(),hospede,LocalDateTime.parse(dto.getDataEntrada()), LocalDateTime.parse(dto.getDataSaida()), dto.getAdicionalVeiculo());
	}
	private CheckinDTO entidadetoDTO(Checkin entidade) {
		HospedeDTO hospede = HospedeServiceImpl.entidadetoDTO(entidade.getHospede());
		return new CheckinDTO(entidade.getId(), hospede,dateToString(entidade.getDataEntrada()),dateToString(entidade.getDataSaida()), entidade.getAdicionalVeiculo());
	}
	private String dateToString(LocalDateTime data) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")        																			.withZone(ZoneId.of("UTC"));
		 return formatter.format(data).toString();
	}
}
