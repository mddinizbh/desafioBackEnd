package com.example.desafiobackend.repositories;

import com.example.desafiobackend.entities.Hospede;

public interface HospedeCustomRepository {
	Hospede findOneByOptionalParams(String nome, String telefone, String documento);
}
