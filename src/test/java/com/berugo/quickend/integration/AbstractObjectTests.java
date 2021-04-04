package com.berugo.quickend.integration;

import com.berugo.quickend.AbstractIntegrationTest;
import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.Object;
import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.testutils.ValidationError;
import com.berugo.quickend.validator.AbstractValidator;
import com.berugo.quickend.validator.CreateOrUpdateObjectValidator;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public abstract class AbstractObjectTests extends AbstractIntegrationTest {
    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObject() throws Exception {
        final ObjectType objectType = this.createAndPostObjectType();
        final Object object = this.createObjectModel();

        object.setObjectTypeExternalId(objectType.getExternalId());

        this.postObjectAndVerifySuccess(object);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectValidationErrors() throws Exception {
        final ObjectType objectType = this.createAndPostObjectType();
        final Object object = this.createObjectModel();

        object.setObjectTypeExternalId(objectType.getExternalId());
        object.setExternalId(Strings.repeat("a", 256));

        this.postObjectAndVerifyValidationErrors(
            object,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_INVALID_SIZE))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectValidationErrors2() throws Exception {
        final Object object = this.createObjectModel();

        this.postObjectAndVerifyValidationErrors(
            object,
            Arrays.asList(new ValidationError(CreateOrUpdateObjectValidator.FIELD_OBJECT_TYPE_EXTERNAL_ID, AbstractValidator.ERROR_CODE_NOT_NULL_NOR_EMPTY))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostObjectFailsIfAlreadyExists() throws Exception {
        final ObjectType objectType = this.createAndPostObjectType();
        final Object object = this.createObjectModel();

        object.setObjectTypeExternalId(objectType.getExternalId());

        this.postObjectAndVerifySuccess(object);

        this.postObjectAndVerifyValidationErrors(
            object,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_ALREADY_EXISTS))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testGetOneObject() throws Exception {
        final ObjectType objectType = this.createAndPostObjectType();
        final Object object = this.createObjectModel();

        object.setObjectTypeExternalId(objectType.getExternalId());

        this.postObjectAndVerifySuccess(object);

        this.getOneObjectAndVerifySuccess(object);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPutObject() throws Exception {
        final ObjectType objectType = this.createAndPostObjectType();
        final Object object = this.createObjectModel();

        object.setObjectTypeExternalId(objectType.getExternalId());

        this.postObjectAndVerifySuccess(object);

        this.getOneObjectAndVerifySuccess(object);

        this.putObjectAndVerifySuccess(object);

        this.getOneObjectAndVerifySuccess(object);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testDeleteObject() throws Exception {
        final ObjectType objectType = this.createAndPostObjectType();
        final Object object = this.createObjectModel();

        object.setObjectTypeExternalId(objectType.getExternalId());

        this.postObjectAndVerifySuccess(object);

        this.getOneObjectAndVerifySuccess(object);

        this.deleteObjectAndVerifySuccess(object);

        this.getOneObjectAndVerifyNotFound(object);
    }

    // Helper Methods

    public ObjectType createAndPostObjectType() throws Exception {
        final Application app = this.createApplicationModel();
        final ObjectType objectType = this.createObjectTypeModel();

        this.postApplicationAndVerifySuccess(app);

        objectType.setApplicationExternalId(app.getExternalId());

        this.postObjectTypeAndVerifySuccess(objectType);

        return objectType;
    }
}
