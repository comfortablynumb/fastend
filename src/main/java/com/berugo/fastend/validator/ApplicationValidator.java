package com.berugo.fastend.validator;

import com.berugo.fastend.model.Application;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("beforeCreateApplicationValidator")
public class ApplicationValidator extends AbstractValidator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Application.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);
    }
}
