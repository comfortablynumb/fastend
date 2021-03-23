package com.berugo.fastend.model.schema;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FieldType {
    private String externalId;

    private boolean nullable = true;

    private boolean searchable = false;

    private Integer min;

    private Integer max;

    private final Map<String, Object> config = new HashMap<>();
}