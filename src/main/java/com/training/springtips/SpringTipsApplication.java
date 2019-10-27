package com.training.springtips;

import com.training.springtips.config.TalkActive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringTipsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(SpringTipsApplication.class, args);

		System.out.println("SpringTipsApplication Started");

		appContext.getBean(TalkActive.class).say();
	}

}
