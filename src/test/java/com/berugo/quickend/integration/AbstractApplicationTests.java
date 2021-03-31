package com.berugo.quickend.integration;

import com.berugo.quickend.AbstractIntegrationTest;
import com.berugo.quickend.model.Application;
import com.berugo.quickend.testutils.ValidationError;
import com.berugo.quickend.validator.AbstractValidator;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractApplicationTests extends AbstractIntegrationTest {
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

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostApplication() throws Exception {
        final Application app = this.createApplicationModel();

        this.postApplicationAndVerifySuccess(app);
    }

    @Test
    @WithMockKeycloakAuth(authorities = "user")
    public void testPostApplicationFailsIfAlreadyExists() throws Exception {
        final Application app = this.createApplicationModel();

        this.postApplicationAndVerifySuccess(app);

        this.postApplicationAndVerifyValidationErrors(
                app,
                Arrays.asList(new ValidationError("externalId", AbstractValidator.ERROR_CODE_ALREADY_EXISTS))
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

    // Helper Methods

    protected Application createApplicationModel() {
        return Application.builder()
                .externalId("some-app")
                .defaultLocale("en_US")
                .build();
    }

    protected void postApplicationAndVerifySuccess(final Application app) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/application")
                .content(this.toJson(app))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                ;

        this.verifySuccessfulResponseFields(app, resultActions);
    }

    protected void postApplicationAndVerifyValidationErrors(
            final Application app,
            final List<ValidationError> expectedErrors
    ) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/application")
                .content(this.toJson(app))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                ;

        for (final ValidationError expectedError : expectedErrors) {
            resultActions.andExpect(jsonPath("$.errors[?(@.field == \"" + expectedError.getField() + "\" && @.errorType == \"" + expectedError.getErrorType() + "\")]").exists());
        }
    }

    protected void putApplicationAndVerifySuccess(final Application app) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .put("/api/application/" + app.getExternalId())
                .content(this.toJson(app))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                ;

        this.verifySuccessfulResponseFields(app, resultActions);
    }

    protected void deleteApplicationAndVerifySuccess(final Application app) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/application/" + app.getExternalId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
        ;
    }

    protected void getOneApplicationAndVerifySuccess(final Application app) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/application/" + app.getExternalId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                ;

        this.verifySuccessfulResponseFields(app, resultActions);
    }

    protected void getOneApplicationAndVerifyNotFound(final Application app) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/application/" + app.getExternalId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
        ;
    }

    protected void verifySuccessfulResponseFields(final Application app, final ResultActions resultActions) throws Exception {
        // NOTE: defaultLocale will be added to availableLocales implicitly after creating / updating the app.
        //       Let's add it here so it matches correctly.

        app.getAvailableLocales().add(app.getDefaultLocale());

        resultActions
                .andExpect(jsonPath("$.externalId").value(app.getExternalId()))
                .andExpect(jsonPath("$.defaultLocale").value(app.getDefaultLocale()))
                .andExpect(jsonPath("$.availableLocales").value(Matchers.hasSize(app.getAvailableLocales().size())))
                .andExpect(jsonPath("$.availableLocales").value(Matchers.containsInAnyOrder(app.getAvailableLocales().toArray())))
        ;
    }

    protected abstract void cleanUp();
}
