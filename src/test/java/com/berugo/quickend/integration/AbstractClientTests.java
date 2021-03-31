package com.berugo.quickend.integration;

import com.berugo.quickend.AbstractIntegrationTest;
import com.berugo.quickend.model.Client;
import com.berugo.quickend.testutils.ValidationError;
import com.berugo.quickend.validator.AbstractValidator;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractClientTests extends AbstractIntegrationTest {
    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostClient() throws Exception {
        final Client client = this.createClientModel();

        this.postClientAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostClientFailsIfAlreadyExists() throws Exception {
        final Client client = this.createClientModel();

        this.postClientAndVerifySuccess(client);

        this.postClientAndVerifyValidationErrors(
                client,
            Arrays.asList(new ValidationError("externalId", AbstractValidator.ERROR_CODE_ALREADY_EXISTS))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testGetOneClient() throws Exception {
        final Client client = this.createClientModel();

        this.postClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPutClient() throws Exception {
        final Client client = this.createClientModel();

        this.postClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);

        this.putClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testDeleteClient() throws Exception {
        final Client client = this.createClientModel();

        this.postClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);

        this.deleteClientAndVerifySuccess(client);

        this.getOneClientAndVerifyNotFound(client);
    }

    // Helper Methods

    protected Client createClientModel() {
        return Client.builder()
            .externalId("some-client")
            .build();
    }

    protected void postClientAndVerifySuccess(final Client client) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post("/api/client")
            .content(this.toJson(client))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
        ;

        this.verifySuccessfulResponseFields(client, resultActions);
    }

    protected void postClientAndVerifyValidationErrors(
        final Client client,
        final List<ValidationError> expectedErrors
    ) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/client")
            .content(this.toJson(client))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
        ;

        this.assertResponseErrorsArePresent(resultActions, expectedErrors);
    }

    protected void putClientAndVerifySuccess(final Client client) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .put("/api/client/" + client.getExternalId())
            .content(this.toJson(client))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;

        this.verifySuccessfulResponseFields(client, resultActions);
    }

    protected void deleteClientAndVerifySuccess(final Client client) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/client/" + client.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
        ;
    }

    protected void getOneClientAndVerifySuccess(final Client client) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/client/" + client.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;

        this.verifySuccessfulResponseFields(client, resultActions);
    }

    protected void getOneClientAndVerifyNotFound(final Client client) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/client/" + client.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }

    protected void verifySuccessfulResponseFields(final Client client, final ResultActions resultActions) throws Exception {
        resultActions
            .andExpect(jsonPath("$.externalId").value(client.getExternalId()))
        ;
    }
}
