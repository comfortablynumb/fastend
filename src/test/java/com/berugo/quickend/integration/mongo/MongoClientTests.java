package com.berugo.quickend.integration.mongo;

import com.berugo.quickend.integration.AbstractClientTests;
import com.berugo.quickend.repository.mongo.ClientRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mongo")
public class MongoClientTests extends AbstractClientTests {

    @Autowired
    private ClientRepository clientRepository;


    @BeforeAll
    public static void setUpAll() {
        startMongoContainer();
    }

    @AfterAll
    public static void tearDownAll() {
        stopAndRemoveMongoContainer();
    }

    protected void cleanUp() {
        this.clientRepository.deleteAll();


    }
}