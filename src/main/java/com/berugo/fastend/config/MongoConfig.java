package com.berugo.fastend.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.berugo.fastend.repository.mongo")
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${mongo.uri}")
    private String mongoConnectionString;

    @Override
    protected String getDatabaseName() {
        return "fastend";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.berugo.fastend");
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        super.configureClientSettings(builder);

        builder.applyConnectionString(new ConnectionString(mongoConnectionString));
    }
}
