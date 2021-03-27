package com.berugo.fastend.validator;

import com.berugo.fastend.model.AbstractModel;
import com.berugo.fastend.model.Application;
import com.google.common.base.Strings;
import lombok.NonNull;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

public abstract class AbstractValidator implements Validator, InitializingBean {
    public static final String ERROR_CODE_NOT_NULL = "not_null";
    public static final String ERROR_CODE_NOT_NULL_NOR_EMPTY = "not_null_nor_empty";

    private String entityName;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.entityName = this.getClass().getSimpleName().replace("Validator", "").toLowerCase();
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        final AbstractModel model = (AbstractModel) target;

        this.rejectIfNullOrEmpty("externalId", model.getExternalId(), errors);
    }

    protected boolean rejectIfNull(final String field, final Object value, final Errors errors) {
        if (value == null) {
            this.addError(field, ERROR_CODE_NOT_NULL, errors);

            return true;
        }

        return false;
    }

    protected boolean rejectIfNullOrEmpty(final String field, final String value, final Errors errors) {
        if (Strings.isNullOrEmpty(value)) {
            this.addError(field, ERROR_CODE_NOT_NULL_NOR_EMPTY, errors);

            return true;
        }

        return false;
    }

    protected boolean rejectIfNullOrEmpty(final String field, final Map value, final Errors errors) {
        if (value == null || value.size() < 1) {
            this.addError(field, ERROR_CODE_NOT_NULL_NOR_EMPTY, errors);

            return true;
        }

        return false;
    }

    protected void addError(final String field, final String code, final Errors errors) {
        errors.rejectValue(field, code, this.getErrorMessageCode(field, code));
    }

    protected String getErrorMessageCode(final String field, final String code) {
        return new StringBuilder()
            .append(this.getEntityName())
            .append(".")
            .append(field)
            .append(".")
            .append(code)
            .toString();
    }

    protected boolean isValidLocale(final String localeName) {
        try {
            LocaleUtils.toLocale(localeName);

            return true;
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    protected String getEntityName() {
        return this.entityName;
    }
}
