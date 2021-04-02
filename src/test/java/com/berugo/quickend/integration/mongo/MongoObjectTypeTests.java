package com.berugo.quickend.integration.mongo;

import com.berugo.quickend.integration.AbstractObjectTypeTests;
import com.berugo.quickend.repository.mongo.ObjectTypeRepository;
import com.berugo.quickend.repository.mongo.ApplicationRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mongo")
public class MongoObjectTypeTests extends AbstractObjectTypeTests {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ObjectTypeRepository objectTypeRepository;


    @BeforeAll
    public static void setUpAll() {
        startMongoContainer();
    }

    @AfterAll
    public static void tearDownAll() {
        stopAndRemoveMongoContainer();
    }

    protected void cleanUp() {
        this.objectTypeRepository.deleteAll();

        this.applicationRepository.deleteAll();
    }
}