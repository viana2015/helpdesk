package com.jrcg.helpdesk.sevices;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrcg.helpdesk.dtos.TecnicoDTO;
import com.jrcg.helpdesk.entity.Pessoa;
import com.jrcg.helpdesk.entity.Tecnico;
import com.jrcg.helpdesk.repositories.PessoaRepository;
import com.jrcg.helpdesk.repositories.TecnicoRepository;
import com.jrcg.helpdesk.sevices.exceptions.DataIntegrityViolationException;
import com.jrcg.helpdesk.sevices.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public List<Tecnico>findAll(){
		return repository.findAll();
	}
	
	public Tecnico findById(Long id) {
		Optional<Tecnico>obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Tecnico não encontrado para este Id:" + id));
		
	}
	
	public Tecnico create(TecnicoDTO objDto) {
		objDto.setId(null);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		validaPorCpfEmail(objDto);
		Tecnico objSalvo = new Tecnico(objDto);
		return repository.save(objSalvo);
		
	}

	public Tecnico update(Long id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
		
	}

	public void delete(Long id) {
		Tecnico obj = findById(id);
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui chamados e não pode ser deletado!");
			
		}
		repository.deleteById(id);
	}

	private void validaPorCpfEmail(TecnicoDTO objDto) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoaRepository.findByEmail(objDto.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}
	}


	
}
