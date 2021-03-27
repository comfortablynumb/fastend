package com.berugo.fastend.validator;

import com.berugo.fastend.model.Application;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.ArrayList;

@Component("beforeCreateApplicationValidator")
public class ApplicationValidator extends AbstractValidator {
    public static final String ERROR_CODE_INVALID_DEFAULT_LOCALE = "invalid_default_locale";
    public static final String ERROR_CODE_INVALID_AVAILABLE_LOCALE = "invalid_available_locale";

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Application.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        super.validate(target, errors);

        final Application app = (Application) target;

        if (this.rejectIfNullOrEmpty("defaultLocale", app.getDefaultLocale(), errors)) {
            return;
        }

        if (!this.isValidLocale(app.getDefaultLocale())) {
            this.addError("defaultLocale", ERROR_CODE_INVALID_DEFAULT_LOCALE, errors);
        }

        if (app.getAvailableLocales() == null) {
            app.setAvailableLocales(new ArrayList<>());
        } else {
            for (final String locale : app.getAvailableLocales()) {
                if (!this.isValidLocale(locale)) {
                    this.addError("availableLocales", ERROR_CODE_INVALID_DEFAULT_LOCALE, errors);

                    break;
                }
            }
        }

        // Set default locale in the available locales list

        if (!app.getAvailableLocales().contains(app.getDefaultLocale())) {
            app.getAvailableLocales().add(app.getDefaultLocale());
        }
    }
}
