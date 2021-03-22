package com.berugo.fastend.validator;

import com.berugo.fastend.model.AbstractModel;
import com.berugo.fastend.model.Application;
import com.google.common.base.Strings;
import lombok.NonNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator implements Validator, InitializingBean {
    public static final String ERROR_CODE_NULL_OR_EMPTY = "null_or_empty";

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

    protected void rejectIfNullOrEmpty(final String field, final String value, final Errors errors) {
        if (Strings.isNullOrEmpty(value)) {
            errors.rejectValue(field, ERROR_CODE_NULL_OR_EMPTY, this.getErrorMessageCode(field, ERROR_CODE_NULL_OR_EMPTY));
        }
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

    protected String getEntityName() {
        return this.entityName;
    }
}
