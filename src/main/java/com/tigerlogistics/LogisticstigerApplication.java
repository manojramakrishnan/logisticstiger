package com.tigerlogistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.tigerlogistics.dao.RepositoryAdminMapper;

@SpringBootApplication
@ComponentScan
public class LogisticstigerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticstigerApplication.class, args);
	}

	
}
