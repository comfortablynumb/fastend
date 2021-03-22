package com.berugo.fastend.model;

import com.berugo.fastend.model.schema.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectType extends AbstractModel {
    private Schema schema;
}
