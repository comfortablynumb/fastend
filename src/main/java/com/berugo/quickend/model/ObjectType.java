package com.berugo.quickend.model;

import com.berugo.quickend.model.schema.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class ObjectType extends AbstractModel {

    private String applicationExternalId;

    @Type(type = "json")
    @Lob
    private Schema schema;
}
