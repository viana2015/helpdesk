package com.jrcg.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrcg.helpdesk.entity.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

}
