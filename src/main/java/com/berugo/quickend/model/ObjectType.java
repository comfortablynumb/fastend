package com.berugo.quickend.model;

import com.berugo.quickend.model.schema.Schema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class ObjectType extends AbstractModel {

    private String applicationExternalId;

    @JsonIgnore
    @Transient
    @javax.persistence.Transient
    private Application application;

    @Type(type = "json")
    @Column(length=1024000)
    private Schema schema;
}
