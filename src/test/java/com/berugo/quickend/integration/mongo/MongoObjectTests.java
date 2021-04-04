package com.berugo.quickend.integration.mongo;

import com.berugo.quickend.integration.AbstractObjectTests;
import com.berugo.quickend.repository.mongo.ApplicationRepository;
import com.berugo.quickend.repository.mongo.ObjectRepository;
import com.berugo.quickend.repository.mongo.ObjectTypeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mongo")
public class MongoObjectTests extends AbstractObjectTests {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ObjectTypeRepository objectTypeRepository;

    @Autowired
    private ObjectRepository objectRepository;


    @BeforeAll
    public static void setUpAll() {
        startMongoContainer();
    }

    @AfterAll
    public static void tearDownAll() {
        stopAndRemoveMongoContainer();
    }

    protected void cleanUp() {
        this.objectRepository.deleteAll();

        this.objectTypeRepository.deleteAll();

        this.applicationRepository.deleteAll();
    }
}