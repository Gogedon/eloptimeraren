package com.eloptimeraren.fetch_elpris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FetchElprisApplication {


	public static void main(String[] args) {
		SpringApplication.run(FetchElprisApplication.class, args);
	}

}
