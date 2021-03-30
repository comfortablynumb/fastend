package com.berugo.quickend.validator;

import com.berugo.quickend.model.Application;
import com.berugo.quickend.repository.AbstractModelRepository;
import com.berugo.quickend.repository.BaseApplicationRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.HashSet;

@Component
public class CreateOrUpdateApplicationValidator extends AbstractCreateOrUpdateValidator<Application> {
    public static final String ERROR_CODE_INVALID_DEFAULT_LOCALE = "invalid_default_locale";
    public static final String ERROR_CODE_INVALID_AVAILABLE_LOCALE = "invalid_available_locale";

    @Autowired
    private BaseApplicationRepository baseApplicationRepository;


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Application.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);

        final Application app = (Application) target;

        if (!this.validateLocales(app, errors)) {
            return;
        }

        if (!this.validateUnique(app, errors)) {
            return;
        }
    }

    protected boolean validateLocales(final Application app, final Errors errors) {
        if (!this.validateNotNullNorEmpty("defaultLocale", app.getDefaultLocale(), errors)) {
            return false;
        }

        if (!this.isValidLocale(app.getDefaultLocale())) {
            this.addError("defaultLocale", ERROR_CODE_INVALID_DEFAULT_LOCALE, errors);
        }

        if (app.getAvailableLocales() == null) {
            app.setAvailableLocales(new HashSet<>());
        } else {
            for (final String locale : app.getAvailableLocales()) {
                if (!this.isValidLocale(locale)) {
                    this.addError("availableLocales", ERROR_CODE_INVALID_AVAILABLE_LOCALE, errors);

                    return false;
                }
            }
        }

        // Set default locale in the available locales list

        app.getAvailableLocales().add(app.getDefaultLocale());

        return true;
    }

    @Override
    protected AbstractModelRepository<Application> getModelRepository() {
        return this.baseApplicationRepository;
    }
}
