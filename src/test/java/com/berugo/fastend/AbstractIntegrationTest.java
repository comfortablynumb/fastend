package com.berugo.fastend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({AbstractIntegrationTest.TestConfig.class})
public abstract class AbstractIntegrationTest {

    private static MongoDBContainer mongoDbContainer;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MongoTemplate mongoTemplate;


    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDBContainer("mongo:4.4.4-bionic");

        mongoDbContainer.setPortBindings(Arrays.asList("37017:27017"));

        mongoDbContainer.start();
    }

    protected String toJson(final Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            fail("Could NOT encode object into JSON.");

            return null;
        }
    }



    @TestConfiguration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public static class TestConfig extends WebSecurityConfigurerAdapter {
        @Bean
        public GrantedAuthoritiesMapper authoritiesMapper() {
            return new NullAuthoritiesMapper();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);

            http.csrf().disable()
                .authorizeRequests();
        }
    }
}
