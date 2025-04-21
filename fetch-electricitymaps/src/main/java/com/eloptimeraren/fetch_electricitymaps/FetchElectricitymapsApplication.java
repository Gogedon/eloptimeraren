package com.eloptimeraren.fetch_electricitymaps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FetchElectricitymapsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FetchElectricitymapsApplication.class, args);
	}

}
