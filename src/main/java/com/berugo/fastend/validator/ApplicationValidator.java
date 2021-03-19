package com.berugo.fastend.validator;

import com.berugo.fastend.model.Application;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateApplicationValidator")
public class ApplicationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Application.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final Application application = (Application) target;

        if (Strings.isNullOrEmpty(application.getExternalId())) {
            errors.rejectValue("externalId", "externalId.empty");
        }
    }
}
