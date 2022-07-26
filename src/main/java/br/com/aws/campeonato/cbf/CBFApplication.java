package br.com.aws.campeonato.cbf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CBFApplication {

	public static void main(String[] args) {
		SpringApplication.run(CBFApplication.class, args);
	}

}
