package com.gino.siscripto;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SisCriptoApplication {
	@Bean
	public ModelMapper modelMapper(){ //crea y devuelve instancias de modelMapper
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SisCriptoApplication.class, args);
	}

}
