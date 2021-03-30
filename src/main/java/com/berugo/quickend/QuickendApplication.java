package com.berugo.quickend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(
	exclude = {
		MongoAutoConfiguration.class,
		H2ConsoleAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class,
		MongoRepositoriesAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
	}
)
public class QuickendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickendApplication.class, args);
	}

}
