package com.project.coursetracker;

import com.project.coursetracker.configurationproperties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.swing.*;
import java.util.Properties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
//public class CoursetrackerApplication implements CommandLineRunner {
public class CoursetrackerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CoursetrackerApplication.class);

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.setProperty("spring.config.on-not-found", "ignore");

//		SpringApplication application = new SpringApplication(CoursetrackerApplication.class);
//
//		application.setDefaultProperties(properties);
//		application.run(args);

		ConfigurableApplicationContext applicationContext = SpringApplication.run(CoursetrackerApplication.class);

		DbConfiguration dbConfiguration = applicationContext.getBean(DbConfiguration.class);
		logger.info(dbConfiguration.toString());

		Environment env = applicationContext.getBean(Environment.class);
		logger.info("Timeout: " + env.getProperty("app.timeout"));

		AppService appService = applicationContext.getBean(AppService.class);
		logger.info(appService.getAppProperties().toString());

//		SpringApplication.run(CoursetrackerApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		logger.info("Application CommandLineRunner has executed...");
//	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			logger.info("CommandLineRunner executed as a bean definition with " + args.length + " arguments");
			for (int i = 0; i < args.length; i++) {
				logger.info("Argument: " + args[i]);
			}
		};
	}

}
