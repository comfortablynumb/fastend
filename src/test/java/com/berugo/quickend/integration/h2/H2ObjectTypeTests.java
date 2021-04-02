package com.berugo.quickend.integration.h2;

import com.berugo.quickend.integration.AbstractObjectTypeTests;
import com.berugo.quickend.repository.jpa.ApplicationRepository;
import com.berugo.quickend.repository.jpa.ObjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
public class H2ObjectTypeTests extends AbstractObjectTypeTests {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ObjectTypeRepository objectTypeRepository;


    protected void cleanUp() {
        this.objectTypeRepository.deleteAll();

        this.applicationRepository.deleteAll();
    }
}