package com.berugo.quickend.model.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Data
@SuperBuilder
@NoArgsConstructor
public class Schema {
    @Builder.Default
    private List<ObjectValidation> validations = new ArrayList<>();

    @Builder.Default
    private Map<String, Field> fields = new HashMap<>();


    public Schema addField(final Field field) {
        this.fields.put(field.getName(), field);

        return this;
    }

    public Field getField(final String fieldName) {
        return this.fields.getOrDefault(fieldName, null);
    }

    public boolean areAllRequiredNonLocalizableFieldsPresent(final Set<String> fields) {
        return this.areAllRequiredFieldsPresent(fields, false);
    }

    public boolean areAllRequiredLocalizableFieldsPresent(final Set<String> fields) {
        return this.areAllRequiredFieldsPresent(fields, true);
    }
    public boolean areAllRequiredFieldsPresent(final Set<String> fields, final boolean localizable) {
        for (final Field field : this.fields.values()) {
            if (field.getType().isLocalizable() != localizable) {
                continue;
            }

            if (!field.getType().isNullable() && !fields.contains(field.getName())) {
                return false;
            }
        }

        return true;
    }
}
