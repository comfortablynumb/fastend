package com.berugo.quickend.model.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder
public class FieldType {
    private String externalId;

    @Builder.Default
    private boolean nullable = true;

    @Builder.Default
    private boolean searchable = false;

    @Builder.Default
    private boolean translatable = false;

    private Integer min;

    private Integer max;

    @Builder.Default
    private Map<String, Object> config = new HashMap<>();
}
