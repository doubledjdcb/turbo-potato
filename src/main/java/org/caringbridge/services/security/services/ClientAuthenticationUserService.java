package org.caringbridge.services.security.services;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;
@Service
public class ClientAuthenticationUserService extends JdbcDaoImpl {
	
	@Qualifier("dataSource")
	@Autowired
	private DataSource dataSource;

	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

}
