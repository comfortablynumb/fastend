package com.berugo.fastend.integration;

import com.berugo.fastend.AbstractIntegrationTest;
import com.berugo.fastend.model.Application;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplicationTests extends AbstractIntegrationTest {
    @Test
    @WithMockKeycloakAuth(
        authorities = "user"
    )
    public void testPostApplication() throws Exception {
        final Application app = Application.builder()
            .externalId("some-app")
            .defaultLocale("en_US")
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/application")
            .content(this.toJson(app))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.externalId").value("some-app"));
    }
}