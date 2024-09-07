package br.com.hoffmoney_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HoffmoneyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoffmoneyBackendApplication.class, args);
	}

}
