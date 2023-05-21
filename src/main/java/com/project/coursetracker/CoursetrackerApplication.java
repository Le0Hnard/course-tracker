package com.project.coursetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import javax.swing.*;
import java.util.Properties;

@SpringBootApplication
public class CoursetrackerApplication {

	private static final Logger log = LoggerFactory.getLogger(CoursetrackerApplication.class);

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.setProperty("spring.config.on-not-found", "ignore");

//		SpringApplication application = new SpringApplication(CoursetrackerApplication.class);
//
//		application.setDefaultProperties(properties);
//		application.run(args);

		ConfigurableApplicationContext applicationContext = SpringApplication.run(CoursetrackerApplication.class);

		DbConfiguration dbConfiguration = applicationContext.getBean(DbConfiguration.class);

		Environment env = applicationContext.getBean(Environment.class);

		log.info("Timeout: " + env.getProperty("app.timeout"));

		log.info(dbConfiguration.toString());

//		SpringApplication.run(CoursetrackerApplication.class, args);
	}

}
