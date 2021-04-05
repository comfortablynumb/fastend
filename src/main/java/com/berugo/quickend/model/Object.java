package com.berugo.quickend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Object extends AbstractModel {

    private String objectTypeExternalId;

    private Integer objectTypeVersion;

    @JsonIgnore
    @Transient
    @javax.persistence.Transient
    private ObjectType objectType;

    @Type(type = "json")
    @Column(length=10240000)
    private Map<String, java.lang.Object> data;


    public String getDataStringValue(final String fieldName) {
        return (String) this.data.getOrDefault(fieldName, null);
    }

    public Boolean getDataBooleanValue(final String fieldName) {
        return (Boolean) this.data.getOrDefault(fieldName, null);
    }
}
