package com.example.desafiobackend.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.desafiobackend.services.DbService;


@Configuration
@Profile("dev")
public class DevConfig {
   
	
	@Autowired
	private DbService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws Exception {
		
		dbService.popularBancoDeDados();
		
		return true;
	}
}
	
