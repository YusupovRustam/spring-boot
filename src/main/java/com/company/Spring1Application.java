package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Spring1Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring1Application.class, args);
	}


//@Scheduled(fixedRate = 2000L)
//	public void startFixedRate(){
//	System.out.println("salom");
//}
//	@Scheduled(fixedDelay = 2000L)
//	public void startfixedDelay(){
//		System.out.println("salom");
//	}

//
//	@Scheduled(cron ="50 31 17 * * *")
//	public void startCron(){
//		System.out.println("salom");
//	}



}
