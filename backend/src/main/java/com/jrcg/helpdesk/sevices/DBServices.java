package com.jrcg.helpdesk.sevices;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrcg.helpdesk.entity.Chamado;
import com.jrcg.helpdesk.entity.Cliente;
import com.jrcg.helpdesk.entity.Tecnico;
import com.jrcg.helpdesk.enums.Perfil;
import com.jrcg.helpdesk.enums.Prioridade;
import com.jrcg.helpdesk.enums.Status;
import com.jrcg.helpdesk.repositories.ChamadoReposistory;
import com.jrcg.helpdesk.repositories.PessoaRepository;

@Service
public class DBServices {

	@Autowired
	PessoaRepository pessoaRepository;
	@Autowired
	private ChamadoReposistory chamadoReposistory;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instaciaDB() {
		
		Tecnico tec1 = new Tecnico(null, "cristiano Viana", "08583582718", "cristiano@teste.com", encoder.encode("123"));
		tec1.addPerfis(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Roberta Sekiguche", "36828831004", "roberta@teste.com", encoder.encode("123"));
		cli1.addPerfis(Perfil.CLIENTE);
		
		Chamado ch1 =  new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "TESTE", "Obs teste", cli1, tec1);
		
		pessoaRepository.saveAll(Arrays.asList(tec1, cli1));
		chamadoReposistory.saveAll(Arrays.asList(ch1));
	}
}
