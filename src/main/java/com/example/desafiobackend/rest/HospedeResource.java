package com.example.desafiobackend.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.desafiobackend.services.HospedeService;
import com.example.desafiobackend.services.dtos.HospedeDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/hospede")
@RequiredArgsConstructor
public class HospedeResource {
	private final HospedeService hospedeService;

	@RequestMapping(value = "/buscarTodos", method = RequestMethod.GET)
	public ResponseEntity<List<HospedeDTO>> buscarTodos() {
		return ResponseEntity.ok().body(hospedeService.findAll());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public HospedeDTO buscarHospede(@PathVariable Long id) {
		return hospedeService.findById(id);
	}
	
	@RequestMapping(value = "/buscarHospede", method = RequestMethod.POST)
	public HospedeDTO buscarHospede(@RequestBody HospedeDTO objDto) {
		return hospedeService.buscarHospedeDto(objDto);
	}

	@RequestMapping(value = "/paginado", method = RequestMethod.GET)
	public ResponseEntity<Page<HospedeDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		return ResponseEntity.ok().body(hospedeService.findPage(page, linesPerPage, orderBy, direction));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody HospedeDTO dto) {
		Long id = hospedeService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody HospedeDTO objDto) {
		hospedeService.alterar(objDto);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		hospedeService.deletar(id);
		return ResponseEntity.noContent().build();

	}
}
