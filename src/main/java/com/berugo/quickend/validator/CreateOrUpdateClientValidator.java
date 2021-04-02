package com.berugo.quickend.validator;

import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.Client;
import com.berugo.quickend.repository.AbstractModelRepository;
import com.berugo.quickend.repository.BaseApplicationRepository;
import com.berugo.quickend.repository.BaseClientRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Optional;

@Component("beforeCreateClientValidator")
public class CreateOrUpdateClientValidator extends AbstractCreateOrUpdateValidator<Client> {
    public static final String FIELD_APPLICATION_EXTERNAL_ID = "applicationExternalId";

    @Autowired
    private BaseApplicationRepository applicationRepository;

    @Autowired
    private BaseClientRepository clientRepository;


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);

        final Client client = (Client) target;

        if (!this.validateUnique(client, errors)) {
            return;
        }

        if (!this.validateNotNullNorEmpty(FIELD_APPLICATION_EXTERNAL_ID, client.getApplicationExternalId(), errors)) {
            return;
        }

        final Optional<Application> app = this.applicationRepository.findByExternalId(client.getApplicationExternalId());

        if (app.isEmpty()) {
            this.addError(FIELD_APPLICATION_EXTERNAL_ID, ERROR_CODE_ENTITY_DOES_NOT_EXIST, errors);

            return;
        }
    }

    @Override
    protected AbstractModelRepository<Client> getModelRepository() {
        return this.clientRepository;
    }
}
