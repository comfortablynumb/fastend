package com.berugo.quickend.validator;

import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.Object;
import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.model.schema.Field;
import com.berugo.quickend.repository.AbstractModelRepository;
import com.berugo.quickend.repository.BaseApplicationRepository;
import com.berugo.quickend.repository.BaseObjectRepository;
import com.berugo.quickend.repository.BaseObjectTypeRepository;
import com.berugo.quickend.service.FieldTypeService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component("beforeCreateObjectValidator")
public class CreateOrUpdateObjectValidator extends AbstractCreateOrUpdateValidator<Object> {
    public static final String FIELD_OBJECT_TYPE_EXTERNAL_ID = "objectTypeExternalId";
    public static final String FIELD_DATA_NAME = "data";
    public static final String FIELD_LOCALIZABLE_DATA_NAME = "localizableData";

    public static final String ERROR_INVALID_OBJECT_TYPE_APPLICATION = "invalid_object_type_application";
    public static final String ERROR_INVALID_OBJECT_TYPE = "invalid_object_type";
    public static final String ERROR_UNSUPPORTED_LOCALE = "unsupported_locale";
    public static final String ERROR_UNEXPECTED_FIELD = "unexpected_field_name";
    public static final String ERROR_FIELD_MUST_NOT_BE_LOCALIZABLE = "field_must_not_be_localizable";
    public static final String ERROR_FIELD_MUST_BE_LOCALIZABLE = "field_must_localizable";
    public static final String ERROR_MISSING_LOCALIZABLE_FIELDS = "missing_localizable_fields";
    public static final String ERROR_MISSING_NON_LOCALIZABLE_FIELDS = "missing_non_localizable_fields";


    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    private BaseApplicationRepository applicationRepository;

    @Autowired
    private BaseObjectTypeRepository objectTypeRepository;

    @Autowired
    private BaseObjectRepository objectRepository;


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Object.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull java.lang.Object target, @NonNull Errors errors) {
        super.validate(target, errors);

        final Object object = (Object) target;

        if (!this.validateUnique(object, errors)) {
            return;
        }

        if (!this.validateObjectType(object, errors)) {
            return;
        }

        if (!this.validateData(object, errors)) {
            return;
        }

        if (!this.validateLocalizableData(object, errors)) {
            return;
        }
    }

    protected boolean validateObjectType(@NonNull final Object object, @NonNull final Errors errors) {
        if (!this.validateNotNullNorEmpty(FIELD_OBJECT_TYPE_EXTERNAL_ID, object.getObjectTypeExternalId(), errors)) {
            return false;
        }

        final Optional<ObjectType> objectType = this.objectTypeRepository.findByExternalId(object.getObjectTypeExternalId());

        if (objectType.isEmpty()) {
            this.addError(FIELD_OBJECT_TYPE_EXTERNAL_ID, ERROR_INVALID_OBJECT_TYPE, errors);

            return false;
        }

        final Optional<Application> application = this.applicationRepository.findByExternalId(objectType.get().getApplicationExternalId());

        if (application.isEmpty()) {
            this.addError(FIELD_OBJECT_TYPE_EXTERNAL_ID, ERROR_INVALID_OBJECT_TYPE_APPLICATION, errors);

            return false;
        }

        objectType.get().setApplication(application.get());
        object.setObjectType(objectType.get());

        return true;
    }

    protected boolean validateData(@NonNull final Object object, @NonNull final Errors errors) {
        final ObjectType objectType = object.getObjectType();
        final Set<String> processedFields = new HashSet<>();

        if (object.getData() != null) {
            for (final Map.Entry<String, java.lang.Object> fieldEntry : object.getData().entrySet()) {
                processedFields.add(fieldEntry.getKey());

                this.validateDataField(
                    objectType,
                    null,
                    fieldEntry.getKey(),
                    fieldEntry.getValue(),
                    errors
                );
            }
        }

        // Verify if any required field is missing
        //
        // @TODO: Tune this validation

        if (!objectType.getSchema().areAllRequiredNonLocalizableFieldsPresent(processedFields)) {
            this.addError(null, ERROR_MISSING_NON_LOCALIZABLE_FIELDS, errors);
        }

        return errors.hasErrors();
    }

    protected boolean validateLocalizableData(@NonNull final Object object, @NonNull final Errors errors) {
        final ObjectType objectType = object.getObjectType();
        final Application application = objectType.getApplication();
        final Set<String> processedFields = new HashSet<>();

        if (object.getLocalizableData() != null) {
            for (final Map.Entry<String, Map<String, java.lang.Object>> localeEntry : object.getLocalizableData().entrySet()) {
                // Validate locale
                final String locale = localeEntry.getKey();

                if (!application.supportsLocale(locale)) {
                    this.addError("localizableData", ERROR_UNSUPPORTED_LOCALE, errors);

                    continue;
                }

                for (final Map.Entry<String, java.lang.Object> fieldEntry : localeEntry.getValue().entrySet()) {
                    processedFields.add(fieldEntry.getKey());

                    this.validateDataField(
                        objectType,
                        locale,
                        fieldEntry.getKey(),
                        fieldEntry.getValue(),
                        errors
                    );
                }
            }
        }

        // Verify if any required field is missing
        //
        // @TODO: Tune this validation

        if (!objectType.getSchema().areAllRequiredLocalizableFieldsPresent(processedFields)) {
            this.addError(null, ERROR_MISSING_LOCALIZABLE_FIELDS, errors);
        }

        return errors.hasErrors();
    }

    protected void validateDataField(
        final ObjectType objectType,
        final String locale,
        final String fieldName,
        final java.lang.Object fieldValue,
        final Errors errors
    ) {
        final String fieldPath = this.getFieldPathForLocale(locale, fieldName);

        // Validate field exists

        final Field field = objectType.getSchema().getField(fieldName);

        if (field == null) {
            this.addError(fieldPath, ERROR_UNEXPECTED_FIELD, errors);

            return;
        }

        // Validate field is (or isn't) localizable as the schema says

        if (locale != null && !field.getType().isLocalizable()) {
            this.addError(fieldPath, ERROR_FIELD_MUST_NOT_BE_LOCALIZABLE, errors);

            return;
        }

        if (fieldPath.equals(FIELD_DATA_NAME) && field.getType().isLocalizable()) {
            this.addError(fieldPath, ERROR_FIELD_MUST_BE_LOCALIZABLE, errors);

            return;
        }

        // Validate not null

        if (!field.getType().isNullable() && !this.validateNotNull(fieldPath, fieldValue, errors)) {
            return;
        }
    }

    protected String getFieldPathForLocale(final String locale, final String fieldName) {
        if (locale == null) {
            return new StringBuilder()
                .append(FIELD_DATA_NAME)
                .append("[")
                .append(fieldName)
                .append("]")
                .toString();
        }

        return new StringBuilder()
            .append(FIELD_LOCALIZABLE_DATA_NAME)
            .append("[")
            .append(locale)
            .append("][")
            .append(fieldName)
            .append("]")
            .toString();
    }

    @Override
    protected AbstractModelRepository<Object> getModelRepository() {
        return this.objectRepository;
    }
}
