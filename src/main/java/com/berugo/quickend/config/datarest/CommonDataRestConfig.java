package com.berugo.quickend.config.datarest;

import com.berugo.quickend.validator.AbstractCreateOrUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonDataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private List<AbstractCreateOrUpdateValidator> createOrUpdateValidators;


    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        // Register all our validators

        for (final AbstractCreateOrUpdateValidator createOrUpdateValidator : createOrUpdateValidators) {
            validatingListener.addValidator("beforeCreate", createOrUpdateValidator);
            validatingListener.addValidator("beforeSave", createOrUpdateValidator);
        }
    }
}