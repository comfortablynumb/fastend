package com.berugo.fastend.validator;

import com.berugo.fastend.model.Client;
import com.berugo.fastend.repository.AbstractModelRepository;
import com.berugo.fastend.repository.BaseClientRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("beforeCreateClientValidator")
public class CreateOrUpdateClientValidator extends AbstractCreateOrUpdateValidator<Client> {
    @Autowired
    private BaseClientRepository baseClientRepository;


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);
    }

    @Override
    protected AbstractModelRepository<Client> getModelRepository() {
        return this.baseClientRepository;
    }
}
