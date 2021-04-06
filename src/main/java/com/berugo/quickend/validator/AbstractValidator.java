package com.berugo.quickend.validator;

import com.berugo.quickend.model.AbstractModel;
import com.berugo.quickend.repository.AbstractModelRepository;
import com.google.common.base.Strings;
import lombok.NonNull;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;
import java.util.Optional;

public abstract class AbstractValidator<M extends AbstractModel> implements Validator, InitializingBean {
    public static final String FIELD_EXTERNAL_ID = "externalId";

    public static final int DEFAULT_EXTERNAL_ID_MIN_SIZE = 1;
    public static final int DEFAULT_EXTERNAL_ID_MAX_SIZE = 255;

    public static final String ERROR_CODE_NOT_NULL = "not_null";
    public static final String ERROR_CODE_NOT_NULL_NOR_EMPTY = "not_null_nor_empty";
    public static final String ERROR_CODE_ALREADY_EXISTS = "already_exists";
    public static final String ERROR_CODE_ENTITY_DOES_NOT_EXIST = "entity_does_not_exist";
    public static final String ERROR_CODE_INVALID_SIZE = "invalid_size";


    private String entityName;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.entityName = this.getClass().getSimpleName().replace("Validator", "").toLowerCase();
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        final AbstractModel model = (AbstractModel) target;

        this.validateNotNullNorEmpty(FIELD_EXTERNAL_ID, model.getExternalId(), errors);

        this.validateSize(
            FIELD_EXTERNAL_ID,
            model.getExternalId(),
            DEFAULT_EXTERNAL_ID_MIN_SIZE,
            DEFAULT_EXTERNAL_ID_MAX_SIZE,
            errors
        );
    }

    protected boolean validateNotNull(final String field, final Object value, final Errors errors) {
        if (value == null) {
            this.addError(field, ERROR_CODE_NOT_NULL, errors);

            return false;
        }

        return true;
    }

    protected boolean validateNotNullNorEmpty(final String field, final String value, final Errors errors) {
        if (Strings.isNullOrEmpty(value)) {
            this.addError(field, ERROR_CODE_NOT_NULL_NOR_EMPTY, errors);

            return false;
        }

        return true;
    }

    protected boolean validateNotNullNorEmpty(final String field, final Map value, final Errors errors) {
        if (value == null || value.size() < 1) {
            this.addError(field, ERROR_CODE_NOT_NULL_NOR_EMPTY, errors);

            return false;
        }

        return true;
    }

    protected boolean validateSize(final String field, final String value, int min, int max, final Errors errors) {
        if (value == null || value.length() < min || value.length() > max) {
            this.addError(field, ERROR_CODE_INVALID_SIZE, errors);

            return false;
        }

        return true;
    }

    protected boolean validateUnique(final M model, final Errors errors) {
        final Optional<M> existentModel = this.getModelRepository().findByExternalId(model.getExternalId());

        if (existentModel.isPresent()
            && (model.getId() == null || !existentModel.get().getId().equals(model.getId()))
        ) {
            this.addError("externalId", ERROR_CODE_ALREADY_EXISTS, errors);

            return false;
        }

        return true;
    }

    protected void addError(final String field, final String code, final Errors errors) {
        if (field == null) {
            errors.reject(code, this.getErrorMessageCode(field, code));

            return;
        }

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

    protected abstract AbstractModelRepository<M> getModelRepository();
}
