package com.jrcg.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jrcg.helpdesk.sevices.DBServices;

@Configuration
@Profile("dev")
public class ProdConfig {

	@Autowired
	private DBServices dbServices;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String value;
	
	@Bean
	public boolean instaciaDB() {
		if (value.equals("create")) {
			this.dbServices.instaciaDB();
		}
		return false;
	}
}
