package com.example.desafiobackend.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.desafiobackend.services.CheckinService;
import com.example.desafiobackend.services.dtos.CheckinDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/checkin")
@RequiredArgsConstructor
public class CheckinResource {
	private final CheckinService checkinService;
	
	@RequestMapping(value = "/todosAtivos", method = RequestMethod.GET)
	public ResponseEntity<List<CheckinDTO>> buscarTodosAtivos() {
		return ResponseEntity.ok().body(checkinService.buscarCheckinAtivo());
	}
	
	@RequestMapping(value = "/todosInativos", method = RequestMethod.GET)
	public ResponseEntity<List<CheckinDTO>> buscarTodosInativos() {
		return ResponseEntity.ok().body(checkinService.buscarCheckinInativo());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody CheckinDTO dto){
		Long id = checkinService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody CheckinDTO objDto) {
		checkinService.alterar(objDto);
		return ResponseEntity.noContent().build();
	}
}
