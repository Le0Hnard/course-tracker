package com.project.coursetracker;

import com.project.coursetracker.configurationproperties.AppProperties;
import com.project.coursetracker.model.Course;
import com.project.coursetracker.model.User;
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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Properties;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CoursetrackerApplication implements CommandLineRunner {
//public class CoursetrackerApplication {

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

	@Override
	public void run(String... args) throws Exception {
//		logger.info("Application CommandLineRunner has executed...");
		Course course = new Course();
		course.setId(1);
		course.setRating(0);

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Course>> violations = validator.validate(course);
		violations.forEach(courseConstraintViolation -> logger.error("A constraint violation has occurred. Violation details: [{}].", courseConstraintViolation));

		User user1 = new User("sbip01", "sbip");
		Set<ConstraintViolation<User>> userViolations = validator.validate(user1);
		logger.error("Password for user1 do not adhere to the password policy");
		userViolations.forEach(constraintViolation -> logger.error("Violation details: [{}].", constraintViolation.getMessage()));

		User user2 = new User("sbip02", "Sbip01$4UDfg");
		userViolations = validator.validate(user2);
		if(userViolations.isEmpty()) {
			logger.info("Password for user2 adhere to the password policy");
		}

		User user3 = new User("sbip03", "Sbip01$4UDfgggg");
		userViolations = validator.validate(user3);
		logger.error("Password for user3 violates maximum repetitive rule");
		userViolations.forEach(constraintViolation -> logger.error("Violation details: [{}].", constraintViolation.getMessage()));

		User user4 = new User("sbip04", "Sbip014UDfgggg");
		userViolations = validator.validate(user4);
		logger.error("Password for user4 violates special character rule");
		userViolations.forEach(constraintViolation -> logger.error("Violation details: [{}].", constraintViolation.getMessage()));
	}

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
