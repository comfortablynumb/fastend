package com.berugo.quickend.integration.mongo;

import com.berugo.quickend.integration.AbstractApplicationTests;
import com.berugo.quickend.repository.mongo.ApplicationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mongo")
public class MongoApplicationTests extends AbstractApplicationTests {

    @Autowired
    private ApplicationRepository applicationRepository;


    @BeforeAll
    public static void setUpAll() {
        startMongoContainer();
    }

    protected void cleanUp() {
        this.applicationRepository.deleteAll();
    }
}