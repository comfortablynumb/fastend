package com.berugo.quickend.integration.h2;

import com.berugo.quickend.integration.AbstractApplicationTests;
import com.berugo.quickend.repository.jpa.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
public class H2ApplicationTests extends AbstractApplicationTests {

    @Autowired
    private ApplicationRepository applicationRepository;


    protected void cleanUp() {
        this.applicationRepository.deleteAll();
    }
}