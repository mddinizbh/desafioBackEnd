package com.example.desafiobackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiobackend.entities.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long> , HospedeCustomRepository  {

}
