package com.berugo.quickend.integration;

import com.berugo.quickend.AbstractIntegrationTest;
import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.testutils.ValidationError;
import com.berugo.quickend.validator.AbstractValidator;
import com.berugo.quickend.validator.CreateOrUpdateObjectTypeValidator;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public abstract class AbstractObjectTypeTests extends AbstractIntegrationTest {
    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType client = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectTypeValidationErrors() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType client = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());
        client.setExternalId(Strings.repeat("a", 256));

        this.postObjectTypeAndVerifyValidationErrors(
            client,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_INVALID_SIZE))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectTypeValidationErrors2() throws Exception {
        final ObjectType client = this.createObjectTypeModel();

        this.postObjectTypeAndVerifyValidationErrors(
            client,
            Arrays.asList(new ValidationError(CreateOrUpdateObjectTypeValidator.FIELD_APPLICATION_EXTERNAL_ID, AbstractValidator.ERROR_CODE_NOT_NULL_NOR_EMPTY))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectTypeFailsIfAlreadyExists() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType client = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(client);

        this.postObjectTypeAndVerifyValidationErrors(
                client,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_ALREADY_EXISTS))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testGetOneObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType client = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(client);

        this.getOneObjectTypeAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPutObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType client = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(client);

        this.getOneObjectTypeAndVerifySuccess(client);

        this.putObjectTypeAndVerifySuccess(client);

        this.getOneObjectTypeAndVerifySuccess(client);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testDeleteObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType client = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        client.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(client);

        this.getOneObjectTypeAndVerifySuccess(client);

        this.deleteObjectTypeAndVerifySuccess(client);

        this.getOneObjectTypeAndVerifyNotFound(client);
    }
}
