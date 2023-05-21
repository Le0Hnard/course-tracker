package com.project.coursetracker;

import com.project.coursetracker.configurationproperties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import javax.swing.*;
import java.util.Properties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
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
		log.info(dbConfiguration.toString());

		Environment env = applicationContext.getBean(Environment.class);
		log.info("Timeout: " + env.getProperty("app.timeout"));

		AppService appService = applicationContext.getBean(AppService.class);
		log.info(appService.getAppProperties().toString());

//		SpringApplication.run(CoursetrackerApplication.class, args);
	}

}
