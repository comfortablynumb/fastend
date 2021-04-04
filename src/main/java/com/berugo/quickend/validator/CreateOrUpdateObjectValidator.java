package com.berugo.quickend.validator;

import com.berugo.quickend.model.Object;
import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.model.schema.Field;
import com.berugo.quickend.repository.AbstractModelRepository;
import com.berugo.quickend.repository.BaseObjectRepository;
import com.berugo.quickend.repository.BaseObjectTypeRepository;
import com.berugo.quickend.service.FieldTypeService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

@Component("beforeCreateObjectValidator")
public class CreateOrUpdateObjectValidator extends AbstractCreateOrUpdateValidator<Object> {
    public static final String FIELD_OBJECT_TYPE_EXTERNAL_ID = "objectTypeExternalId";

    public static final String ERROR_INVALID_OBJECT_TYPE = "invalid_object_type";
    public static final String ERROR_UNEXPECTED_FIELD_NAME = "unexpected_field_name";


    @Autowired
    private FieldTypeService fieldTypeService;

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

        object.setObjectType(objectType.get());

        return true;
    }

    protected boolean validateData(@NonNull final Object object, @NonNull final Errors errors) {
        final ObjectType objectType = object.getObjectType();
        boolean hasErrors = false;

        for (final Map.Entry<String, java.lang.Object> fieldEntry : object.getData().entrySet()) {
            final String fieldName = fieldEntry.getKey();
            final java.lang.Object fieldValue = fieldEntry.getValue();
            final String fieldPath = "data[" + fieldName + "]";
            final Field field = objectType.getSchema().getField(fieldName);

            // Validate this object type's schema knows this field

            if (field == null) {
                this.addError(fieldPath, ERROR_UNEXPECTED_FIELD_NAME, errors);

                hasErrors = true;

                continue;
            }

            // Validate not null

            if (!field.getType().isNullable() && !this.validateNotNull(fieldPath, fieldValue, errors)) {
                hasErrors = true;

                continue;
            }
        }

        return hasErrors;
    }

    @Override
    protected AbstractModelRepository<Object> getModelRepository() {
        return this.objectRepository;
    }
}
