package com.berugo.quickend.integration.h2;

import com.berugo.quickend.integration.AbstractClientTests;
import com.berugo.quickend.repository.jpa.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
public class H2ClientTests extends AbstractClientTests {

    @Autowired
    private ClientRepository clientRepository;


    protected void cleanUp() {
        this.clientRepository.deleteAll();
    }
}