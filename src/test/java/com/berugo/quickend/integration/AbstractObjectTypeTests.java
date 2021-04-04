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
        final ObjectType objectType = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        objectType.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(objectType);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectTypeValidationErrors() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType objectType = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        objectType.setApplicationExternalId(app.getExternalId());
        objectType.setExternalId(Strings.repeat("a", 256));

        this.postObjectTypeAndVerifyValidationErrors(
            objectType,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_INVALID_SIZE))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectTypeValidationErrors2() throws Exception {
        final ObjectType objectType = this.createObjectTypeModel();

        this.postObjectTypeAndVerifyValidationErrors(
            objectType,
            Arrays.asList(new ValidationError(CreateOrUpdateObjectTypeValidator.FIELD_APPLICATION_EXTERNAL_ID, AbstractValidator.ERROR_CODE_NOT_NULL_NOR_EMPTY))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectTypeFailsIfAlreadyExists() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType objectType = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        objectType.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(objectType);

        this.postObjectTypeAndVerifyValidationErrors(
                objectType,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_ALREADY_EXISTS))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testGetOneObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType objectType = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        objectType.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(objectType);

        this.getOneObjectTypeAndVerifySuccess(objectType);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPutObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType objectType = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        objectType.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(objectType);

        this.getOneObjectTypeAndVerifySuccess(objectType);

        this.putObjectTypeAndVerifySuccess(objectType);

        this.getOneObjectTypeAndVerifySuccess(objectType);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testDeleteObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType objectType = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        objectType.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(objectType);

        this.getOneObjectTypeAndVerifySuccess(objectType);

        this.deleteObjectTypeAndVerifySuccess(objectType);

        this.getOneObjectTypeAndVerifyNotFound(objectType);
    }
}
