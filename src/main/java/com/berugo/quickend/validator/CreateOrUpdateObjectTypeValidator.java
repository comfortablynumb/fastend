package com.berugo.quickend.validator;

import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.model.schema.Field;
import com.berugo.quickend.repository.AbstractModelRepository;
import com.berugo.quickend.repository.BaseApplicationRepository;
import com.berugo.quickend.repository.BaseObjectTypeRepository;
import com.berugo.quickend.schema.fieldtype.AbstractFieldType;
import com.berugo.quickend.service.FieldTypeService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

@Component("beforeCreateObjectTypeValidator")
public class CreateOrUpdateObjectTypeValidator extends AbstractCreateOrUpdateValidator<ObjectType> {
    public static final String ERROR_CODE_INVALID_FIELD_TYPE = "invalid_field_type";
    public static final String FIELD_APPLICATION_EXTERNAL_ID = "applicationExternalId";


    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    private BaseApplicationRepository applicationRepository;

    @Autowired
    private BaseObjectTypeRepository objectTypeRepository;


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ObjectType.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);

        final ObjectType objectType = (ObjectType) target;

        if (!this.validateUnique(objectType, errors)) {
            return;
        }

        if (!this.validateApplication(objectType, errors)) {
            return;
        }

        if (!this.validateSchema(objectType, errors)) {
            return;
        }
    }

    protected boolean validateApplication(@NonNull ObjectType objectType, @NonNull Errors errors) {
        if (!this.validateNotNullNorEmpty(FIELD_APPLICATION_EXTERNAL_ID, objectType.getApplicationExternalId(), errors)) {
            return false;
        }

        final Optional<Application> app = this.applicationRepository.findByExternalId(objectType.getApplicationExternalId());

        if (app.isEmpty()) {
            this.addError(FIELD_APPLICATION_EXTERNAL_ID, ERROR_CODE_ENTITY_DOES_NOT_EXIST, errors);

            return false;
        }

        return true;
    }

    protected boolean validateSchema(@NonNull ObjectType objectType, @NonNull Errors errors) {
        if (!this.validateNotNull("schema", objectType.getSchema(), errors)) {
            return false;
        }

        if (!this.validateNotNullNorEmpty("schema.fields", objectType.getSchema().getFields(), errors)) {
            return false;
        }

        for (final Map.Entry<String, Field> field : objectType.getSchema().getFields().entrySet()) {
            final String errorFieldName = "schema.fields[" + field.getKey() + "]";

            if (!this.validateNotNull(errorFieldName, field.getValue(), errors)) {
                return false;
            }

            if (!this.validateNotNull(errorFieldName, field.getValue().getType(), errors)) {
                return false;
            }

            final AbstractFieldType fieldType = this.fieldTypeService.getFieldType(field.getValue().getType().getExternalId());

            if (fieldType == null) {
                errors.rejectValue(
                    errorFieldName + ".type",
                    ERROR_CODE_INVALID_FIELD_TYPE,
                    this.getErrorMessageCode(errorFieldName, ERROR_CODE_INVALID_FIELD_TYPE)
                );

                return false;
            }
        }

        return true;
    }

    @Override
    protected AbstractModelRepository<ObjectType> getModelRepository() {
        return this.objectTypeRepository;
    }
}
