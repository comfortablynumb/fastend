package com.berugo.quickend.integration.mongo;

import com.berugo.quickend.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Arrays;

@ActiveProfiles("mongo")
public abstract class AbstractMongoTest extends AbstractIntegrationTest {
    private static MongoDBContainer mongoDbContainer;


    @Autowired
    protected MongoTemplate mongoTemplate;


    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDBContainer("mongo:4.4.4-bionic");

        mongoDbContainer.setPortBindings(Arrays.asList("37017:27017"));

        mongoDbContainer.start();
    }
}
