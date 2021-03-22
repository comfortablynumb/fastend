package com.berugo.fastend.validator;

import com.berugo.fastend.model.Client;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("beforeCreateClientValidator")
public class ClientValidator extends AbstractValidator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);
    }
}
