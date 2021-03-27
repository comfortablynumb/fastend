package com.berugo.fastend.model;

import com.berugo.fastend.model.schema.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ObjectType extends AbstractModel {
    private Schema schema;
}
