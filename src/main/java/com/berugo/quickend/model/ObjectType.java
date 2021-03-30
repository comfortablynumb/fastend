package com.berugo.quickend.model;

import com.berugo.quickend.model.schema.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class ObjectType extends AbstractModel {

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Schema schema;
}
