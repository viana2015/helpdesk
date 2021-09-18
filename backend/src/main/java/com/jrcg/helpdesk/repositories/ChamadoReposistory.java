package com.jrcg.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrcg.helpdesk.entity.Chamado;

@Repository
public interface ChamadoReposistory extends JpaRepository<Chamado, Long> {

}
