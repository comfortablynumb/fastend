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

    public Set<String> getMissingNonLocalizableFieldNames(final Set<String> fields) {
        return this.getMissingFieldNames(fields, false);
    }

    public Set<String> getMissingLocalizableFieldNames(final Set<String> fields) {
        return this.getMissingFieldNames(fields, true);
    }
    public Set<String> getMissingFieldNames(final Set<String> fields, final boolean localizable) {
        final Set<String> missingFields = new HashSet<>();

        for (final Field field : this.fields.values()) {
            if (field.getType().isNullable() || field.getType().isLocalizable() != localizable) {
                continue;
            }

            if (!fields.contains(field.getName())) {
                missingFields.add(field.getName());
            }
        }

        return missingFields;
    }
}
