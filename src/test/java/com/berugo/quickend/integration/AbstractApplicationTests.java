package com.berugo.quickend.integration;

import com.berugo.quickend.AbstractIntegrationTest;
import com.berugo.quickend.model.Application;
import com.berugo.quickend.testutils.ValidationError;
import com.berugo.quickend.validator.AbstractValidator;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public abstract class AbstractApplicationTests extends AbstractIntegrationTest {
    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostApplication() throws Exception {
        final Application app = this.createApplicationModel();

        this.postApplicationAndVerifySuccess(app);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostApplicationValidationErrors() throws Exception {
        final Application app = this.createApplicationModel();

        app.setExternalId(Strings.repeat("a", 256));

        this.postApplicationAndVerifyValidationErrors(
            app,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_INVALID_SIZE))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostApplicationFailsIfAlreadyExists() throws Exception {
        final Application app = this.createApplicationModel();

        this.postApplicationAndVerifySuccess(app);

        this.postApplicationAndVerifyValidationErrors(
            app,
            Arrays.asList(new ValidationError(AbstractValidator.FIELD_EXTERNAL_ID, AbstractValidator.ERROR_CODE_ALREADY_EXISTS))
        );
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testGetOneApplication() throws Exception {
        final Application app = this.createApplicationModel();

        this.postApplicationAndVerifySuccess(app);

        this.getOneApplicationAndVerifySuccess(app);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPutApplication() throws Exception {
        final Application app = this.createApplicationModel();

        this.postApplicationAndVerifySuccess(app);

        this.getOneApplicationAndVerifySuccess(app);

        app.setDefaultLocale("es_AR");

        this.putApplicationAndVerifySuccess(app);

        this.getOneApplicationAndVerifySuccess(app);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testDeleteApplication() throws Exception {
        final Application app = this.createApplicationModel();

        this.postApplicationAndVerifySuccess(app);

        this.getOneApplicationAndVerifySuccess(app);

        this.deleteApplicationAndVerifySuccess(app);

        this.getOneApplicationAndVerifyNotFound(app);
    }
}
