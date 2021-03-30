package com.berugo.quickend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureMockMvc
@Import({AbstractIntegrationTest.TestConfig.class})
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;


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
