package com.berugo.fastend.validator;

import com.berugo.fastend.model.ObjectType;
import com.berugo.fastend.model.schema.Field;
import com.berugo.fastend.repository.AbstractModelRepository;
import com.berugo.fastend.repository.BaseObjectTypeRepository;
import com.berugo.fastend.schema.fieldtype.AbstractFieldType;
import com.berugo.fastend.service.FieldTypeService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Map;

@Component("beforeCreateObjectTypeValidator")
public class CreateOrUpdateObjectTypeValidator extends AbstractCreateOrUpdateValidator<ObjectType> {
    public static final String ERROR_CODE_INVALID_FIELD_TYPE = "invalid_field_type";


    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    private BaseObjectTypeRepository baseObjectTypeRepository;


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ObjectType.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);

        final ObjectType objectType = (ObjectType) target;

        if (!this.validateNotNull("schema", objectType.getSchema(), errors)) {
            return;
        }

        if (!this.validateNotNullNorEmpty("schema.fields", objectType.getSchema().getFields(), errors)) {
            return;
        }

        for (final Map.Entry<String, Field> field : objectType.getSchema().getFields().entrySet()) {
            final String errorFieldName = "schema.fields[" + field.getKey() + "]";

            if (!this.validateNotNull(errorFieldName, field.getValue(), errors)) {
                return;
            }

            if (!this.validateNotNull(errorFieldName, field.getValue().getType(), errors)) {
                return;
            }

            final AbstractFieldType fieldType = this.fieldTypeService.getFieldType(field.getValue().getType().getExternalId());

            if (fieldType == null) {
                errors.rejectValue(
                    errorFieldName + ".type",
                    ERROR_CODE_INVALID_FIELD_TYPE,
                    this.getErrorMessageCode(errorFieldName, ERROR_CODE_INVALID_FIELD_TYPE)
                );

                return;
            }
        }
    }

    @Override
    protected AbstractModelRepository<ObjectType> getModelRepository() {
        return this.baseObjectTypeRepository;
    }
}
