package com.berugo.quickend;

import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.Client;
import com.berugo.quickend.model.Object;
import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.model.schema.Field;
import com.berugo.quickend.model.schema.FieldType;
import com.berugo.quickend.model.schema.Schema;
import com.berugo.quickend.testutils.ValidationError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    // Per Entity Utility Methods

    // :: Application

    protected Application createApplicationModel() {
        final Application app = Application.builder()
            .externalId("some-app")
            .defaultLocale("en_US")
            .build();

        app.addAvailableLocale("es_AR");

        return app;
    }

    protected void postApplicationAndVerifySuccess(final Application app) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post("/api/application")
            .content(this.toJson(app))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            ;

        this.verifyApplicationSuccessfulResponseFields(app, resultActions);
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

        this.assertResponseErrorsArePresent(resultActions, expectedErrors);
    }

    protected void putApplicationAndVerifySuccess(final Application app) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .put("/api/application/" + app.getExternalId())
            .content(this.toJson(app))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;

        this.verifyApplicationSuccessfulResponseFields(app, resultActions);
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

        this.verifyApplicationSuccessfulResponseFields(app, resultActions);
    }

    protected void getOneApplicationAndVerifyNotFound(final Application app) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/application/" + app.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }

    protected void verifyApplicationSuccessfulResponseFields(final Application app, final ResultActions resultActions) throws Exception {
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

    // :: Client

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

        this.verifyClientSuccessfulResponseFields(client, resultActions);
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

        this.verifyClientSuccessfulResponseFields(client, resultActions);
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

        this.verifyClientSuccessfulResponseFields(client, resultActions);
    }

    protected void getOneClientAndVerifyNotFound(final Client client) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/client/" + client.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }

    protected void verifyClientSuccessfulResponseFields(final Client client, final ResultActions resultActions) throws Exception {
        resultActions
            .andExpect(jsonPath("$.externalId").value(client.getExternalId()))
            .andExpect(jsonPath("$.applicationExternalId").value(client.getApplicationExternalId()))
        ;
    }

    // :: Object Type

    protected ObjectType createObjectTypeModel() {
        final FieldType stringFieldType = FieldType.builder().externalId("string").build();
        final FieldType localizableStringFieldType = FieldType.builder().externalId("string").localizable(true).build();
        final FieldType booleanFieldType = FieldType.builder().externalId("boolean").build();

        return ObjectType.builder()
            .externalId("some-object-type")
            .schema(
                Schema.builder().build()
                .addField(Field.builder().name("title").type(stringFieldType).build())
                .addField(Field.builder().name("localizableTitle").type(localizableStringFieldType).build())
                .addField(Field.builder().name("active").type(booleanFieldType).build())
            )
            .build();
    }

    protected void postObjectTypeAndVerifySuccess(final ObjectType objectType) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post("/api/object_type")
            .content(this.toJson(objectType))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            ;

        this.verifyObjectTypeSuccessfulResponseFields(objectType, resultActions);
    }

    protected void postObjectTypeAndVerifyValidationErrors(
        final ObjectType objectType,
        final List<ValidationError> expectedErrors
    ) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/object_type")
            .content(this.toJson(objectType))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            ;

        this.assertResponseErrorsArePresent(resultActions, expectedErrors);
    }

    protected void putObjectTypeAndVerifySuccess(final ObjectType objectType) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .put("/api/object_type/" + objectType.getExternalId())
            .content(this.toJson(objectType))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;

        this.verifyObjectTypeSuccessfulResponseFields(objectType, resultActions);
    }

    protected void deleteObjectTypeAndVerifySuccess(final ObjectType objectType) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/object_type/" + objectType.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
        ;
    }

    protected void getOneObjectTypeAndVerifySuccess(final ObjectType objectType) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/object_type/" + objectType.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;

        this.verifyObjectTypeSuccessfulResponseFields(objectType, resultActions);
    }

    protected void getOneObjectTypeAndVerifyNotFound(final ObjectType objectType) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/object_type/" + objectType.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }

    protected void verifyObjectTypeSuccessfulResponseFields(final ObjectType objectType, final ResultActions resultActions) throws Exception {
        resultActions
            .andExpect(jsonPath("$.externalId").value(objectType.getExternalId()))
            .andExpect(jsonPath("$.applicationExternalId").value(objectType.getApplicationExternalId()))
        ;
    }

    // :: Object

    protected Object createObjectModel() {
        final Map<String, java.lang.Object> objectData = new HashMap<>();
        final Map<String, Map<String, java.lang.Object>> objectLocalizableData = new HashMap<>();

        objectData.put("title", "Some Title");
        objectData.put("active", true);

        objectLocalizableData.put("en_US", new HashMap<>());
        objectLocalizableData.put("es_AR", new HashMap<>());

        objectLocalizableData.get("en_US").put("localizableTitle", "English Title");
        objectLocalizableData.get("es_AR").put("localizableTitle", "T??tulo en Espa??ol");

        return Object.builder()
            .externalId("some-object")
            .data(objectData)
            .localizableData(objectLocalizableData)
            .build();
    }

    protected void postObjectAndVerifySuccess(final Object object) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post("/api/object")
            .content(this.toJson(object))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            ;

        this.verifyObjectSuccessfulResponseFields(object, resultActions);
    }

    protected void postObjectAndVerifyValidationErrors(
        final Object object,
        final List<ValidationError> expectedErrors
    ) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/object")
            .content(this.toJson(object))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            ;

        this.assertResponseErrorsArePresent(resultActions, expectedErrors);
    }

    protected void putObjectAndVerifySuccess(final Object object) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .put("/api/object/" + object.getExternalId())
            .content(this.toJson(object))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;

        this.verifyObjectSuccessfulResponseFields(object, resultActions);
    }

    protected void deleteObjectAndVerifySuccess(final Object object) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/object/" + object.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
        ;
    }

    protected void getOneObjectAndVerifySuccess(final Object object) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/object/" + object.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;

        this.verifyObjectSuccessfulResponseFields(object, resultActions);
    }

    protected void getOneObjectAndVerifyNotFound(final Object object) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/object/" + object.getExternalId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }

    protected void verifyObjectSuccessfulResponseFields(
        final Object object,
        final ResultActions resultActions
    ) throws Exception {
        resultActions
            .andExpect(jsonPath("$.externalId").value(object.getExternalId()))
            .andExpect(jsonPath("$.data[\"title\"]").value(object.getDataStringValue("title")))
            .andExpect(jsonPath("$.data[\"active\"]").value(object.getDataBooleanValue("active")))
            .andExpect(jsonPath("$.localizableData[\"en_US\"][\"localizableTitle\"]").value(object.getLocalizableDataValue("en_US", "localizableTitle")))
            .andExpect(jsonPath("$.localizableData[\"es_AR\"][\"localizableTitle\"]").value(object.getLocalizableDataValue("es_AR", "localizableTitle")))
        ;
    }

    // Utility Methods

    protected String toJson(final java.lang.Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            fail("Could NOT encode object into JSON. Error: " + e.getMessage());

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
