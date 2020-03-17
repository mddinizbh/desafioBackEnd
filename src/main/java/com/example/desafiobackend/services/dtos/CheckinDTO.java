package com.example.desafiobackend.services.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckinDTO implements Serializable {

	private Long id;
	private HospedeDTO hospede;
	private String dataEntrada;
	private String dataSaida;
	private Long ultimaHospedagem;
	private Long valorTotalGasto;
	private Boolean adicionalVeiculo;

	public CheckinDTO(Long id, HospedeDTO hospede, String dataEntrada, String dataSaida, Boolean adiconalVeiculo) {
		this.id = id;
		this.hospede = hospede;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.adicionalVeiculo = adiconalVeiculo;
	}

}
