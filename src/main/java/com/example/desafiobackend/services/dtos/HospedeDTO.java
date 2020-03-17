package com.example.desafiobackend.services.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospedeDTO implements Serializable {
	private Long id;
	private String nome;
	private String telefone;
	private String documento;
}
