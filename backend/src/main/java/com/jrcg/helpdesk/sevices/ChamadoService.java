package com.jrcg.helpdesk.sevices;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrcg.helpdesk.dtos.ChamadoDTO;
import com.jrcg.helpdesk.entity.Chamado;
import com.jrcg.helpdesk.entity.Cliente;
import com.jrcg.helpdesk.entity.Tecnico;
import com.jrcg.helpdesk.enums.Prioridade;
import com.jrcg.helpdesk.enums.Status;
import com.jrcg.helpdesk.repositories.ChamadoReposistory;
import com.jrcg.helpdesk.sevices.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoReposistory reposistory;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(long id) {
		Optional<Chamado> obj = reposistory.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado para este id: " + id));
	}
	
	public List<Chamado>findAll() {
		return reposistory.findAll();
	}
	
	public Chamado create(ChamadoDTO objDTO ) {
		return reposistory.save(newChamado(objDTO));
	}
	
	public Chamado update(Long id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);
		return reposistory.save(oldObj);
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		if (obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		return chamado;
	}

}
