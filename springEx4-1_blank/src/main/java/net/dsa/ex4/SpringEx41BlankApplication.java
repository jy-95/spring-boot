package net.dsa.ex4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringEx41BlankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEx41BlankApplication.class, args);
	}

}
