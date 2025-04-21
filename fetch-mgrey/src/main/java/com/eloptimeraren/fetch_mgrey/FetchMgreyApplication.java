package com.eloptimeraren.fetch_mgrey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FetchMgreyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FetchMgreyApplication.class, args);
	}

}
