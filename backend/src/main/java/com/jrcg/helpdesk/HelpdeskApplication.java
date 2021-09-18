package com.jrcg.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jrcg.helpdesk.entity.Chamado;
import com.jrcg.helpdesk.entity.Cliente;
import com.jrcg.helpdesk.entity.Tecnico;
import com.jrcg.helpdesk.enums.Perfil;
import com.jrcg.helpdesk.enums.Prioridade;
import com.jrcg.helpdesk.enums.Status;
import com.jrcg.helpdesk.repositories.ChamadoReposistory;
import com.jrcg.helpdesk.repositories.ClienteRepository;
import com.jrcg.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ChamadoReposistory chamadoReposistory;
	
	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "cristiano Viana", "08583582718", "cristiano@teste.com", "12312");
		tec1.addPerfis(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Roberta Sekiguche", "36828831004", "roberta@teste.com", "123");
		cli1.addPerfis(Perfil.CLIENTE);
		
		Chamado ch1 =  new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "TESTE", "Obs teste", cli1, tec1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoReposistory.saveAll(Arrays.asList(ch1));
		
	}

}
