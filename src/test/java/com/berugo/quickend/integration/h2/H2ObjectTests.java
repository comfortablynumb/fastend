package com.berugo.quickend.integration.h2;

import com.berugo.quickend.integration.AbstractObjectTests;
import com.berugo.quickend.repository.jpa.ApplicationRepository;
import com.berugo.quickend.repository.jpa.ObjectRepository;
import com.berugo.quickend.repository.jpa.ObjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
public class H2ObjectTests extends AbstractObjectTests {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ObjectTypeRepository objectTypeRepository;

    @Autowired
    private ObjectRepository objectRepository;


    protected void cleanUp() {
        this.objectRepository.deleteAll();

        this.objectTypeRepository.deleteAll();

        this.applicationRepository.deleteAll();
    }
}