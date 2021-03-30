package com.berugo.quickend.model.schema;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FieldType {
    private String externalId;

    private boolean nullable = true;

    private boolean searchable = false;

    private boolean translatable = false;

    private Integer min;

    private Integer max;

    private final Map<String, Object> config = new HashMap<>();
}
