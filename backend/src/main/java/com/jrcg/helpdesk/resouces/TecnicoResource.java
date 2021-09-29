package com.jrcg.helpdesk.resouces;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jrcg.helpdesk.dtos.TecnicoDTO;
import com.jrcg.helpdesk.entity.Tecnico;
import com.jrcg.helpdesk.sevices.TecnicoService;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService service;
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>>findAll(){
		List<Tecnico>list = service.findAll();
		List<TecnicoDTO>listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TecnicoDTO>findById(@PathVariable Long id){
		Tecnico obj = service.findById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
		
	}
	
	@PostMapping
	public ResponseEntity<TecnicoDTO>create(@Valid @RequestBody TecnicoDTO objDto){
		Tecnico newObj = service.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(objDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TecnicoDTO>update(@PathVariable Long id, @Valid @RequestBody TecnicoDTO objDTO  ) {
		Tecnico obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
