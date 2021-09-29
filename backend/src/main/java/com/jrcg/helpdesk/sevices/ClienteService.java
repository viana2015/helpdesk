package com.jrcg.helpdesk.sevices;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrcg.helpdesk.dtos.ClienteDTO;
import com.jrcg.helpdesk.entity.Cliente;
import com.jrcg.helpdesk.entity.Pessoa;
import com.jrcg.helpdesk.repositories.ClienteRepository;
import com.jrcg.helpdesk.repositories.PessoaRepository;
import com.jrcg.helpdesk.sevices.exceptions.DataIntegrityViolationException;
import com.jrcg.helpdesk.sevices.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public List<Cliente>findAll(){
		return repository.findAll();
	}
	
	public Cliente findById(Long id) {
		Optional<Cliente>obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Tecnico não encontrado para este Id:" + id));
		
	}
	
	public Cliente create(ClienteDTO objDto) {
		objDto.setId(null);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		validaPorCpfEmail(objDto);
		Cliente objSalvo = new Cliente(objDto);
		return repository.save(objSalvo);
		
	}

	public Cliente update(Long id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
		
	}

	public void delete(Long id) {
		Cliente obj = findById(id);
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui chamados e não pode ser deletado!");
			
		}
		repository.deleteById(id);
	}

	private void validaPorCpfEmail(ClienteDTO objDto) {
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
