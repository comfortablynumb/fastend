package com.berugo.fastend.validator;

import com.berugo.fastend.model.ObjectType;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("beforeCreateObjectTypeValidator")
public class ObjectTypeValidator extends AbstractValidator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ObjectType.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);
    }
}
