package com.berugo.quickend.integration;

import com.berugo.quickend.AbstractIntegrationTest;
import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.Client;
import com.berugo.quickend.testutils.ValidationError;
import com.berugo.quickend.validator.AbstractValidator;
import com.berugo.quickend.validator.CreateOrUpdateClientValidator;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public abstract class AbstractClientTests extends AbstractIntegrationTest {
    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostClient() throws Exception {
        final Application app = this.createApplicationModel();
        final Client client = this.createClientModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postClientAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostClientValidationErrors() throws Exception {
        final Application app = this.createApplicationModel();
        final Client client = this.createClientModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());
        client.setExternalId(Strings.repeat("a", 256));

        this.postClientAndVerifyValidationErrors(
            client,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_INVALID_SIZE))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostClientValidationErrors2() throws Exception {
        final Client client = this.createClientModel();

        this.postClientAndVerifyValidationErrors(
            client,
            Arrays.asList(new ValidationError(CreateOrUpdateClientValidator.FIELD_APPLICATION_EXTERNAL_ID, AbstractValidator.ERROR_CODE_NOT_NULL_NOR_EMPTY))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostClientFailsIfAlreadyExists() throws Exception {
        final Application app = this.createApplicationModel();
        final Client client = this.createClientModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postClientAndVerifySuccess(client);

        this.postClientAndVerifyValidationErrors(
                client,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_ALREADY_EXISTS))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testGetOneClient() throws Exception {
        final Application app = this.createApplicationModel();
        final Client client = this.createClientModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPutClient() throws Exception {
        final Application app = this.createApplicationModel();
        final Client client = this.createClientModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);

        this.putClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testDeleteClient() throws Exception {
        final Application app = this.createApplicationModel();
        final Client client = this.createClientModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postClientAndVerifySuccess(client);

        this.getOneClientAndVerifySuccess(client);

        this.deleteClientAndVerifySuccess(client);

        this.getOneClientAndVerifyNotFound(client);
    }
}
