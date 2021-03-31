package com.berugo.quickend;

import com.berugo.quickend.testutils.ValidationError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Import({AbstractIntegrationTest.TestConfig.class})
public abstract class AbstractIntegrationTest {

    protected static MongoDBContainer mongoDbContainer;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        // Clean up before each test runs, just to be sure

        this.cleanUp();
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test runs, just to be sure

        this.cleanUp();
    }

    // Utility methods

    protected String toJson(final Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            fail("Could NOT encode object into JSON.");

            return null;
        }
    }

    protected void assertResponseErrorsArePresent(
        final ResultActions resultActions,
        final List<ValidationError> expectedErrors
    ) throws Exception {
        for (final ValidationError expectedError : expectedErrors) {
            resultActions.andExpect(jsonPath("$.errors[?(@.field == \"" + expectedError.getField() + "\" && @.errorType == \"" + expectedError.getErrorType() + "\")]").exists());
        }
    }

    public static void startMongoContainer() {
        mongoDbContainer = new MongoDBContainer("mongo:4.4.4-bionic");

        mongoDbContainer.setPortBindings(Arrays.asList("37017:27017"));

        mongoDbContainer.start();
    }

    public static void stopAndRemoveMongoContainer() {
        mongoDbContainer.stop();
    }

    // Abstract methods

    protected abstract void cleanUp();

    // Inner classes



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
