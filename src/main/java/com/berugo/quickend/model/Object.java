package com.berugo.quickend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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

    @Type(type = "json")
    @Column(length=10240000)
    private Map<String, Map<String, java.lang.Object>> localizableData;



    public String getDataStringValue(final String fieldName) {
        return (String) this.getDataValue(fieldName);
    }

    public Boolean getDataBooleanValue(final String fieldName) {
        return (Boolean) this.getDataValue(fieldName);
    }

    public java.lang.Object getDataValue(final String fieldName) {
        return this.data.getOrDefault(fieldName, null);
    }

    public String getLocalizableDataStringValue(final String locale, final String fieldName) {
        return (String) this.getLocalizableDataValue(locale, fieldName);
    }

    public Boolean getLocalizableDataBooleanValue(final String locale, final String fieldName) {
        return (Boolean) this.getLocalizableDataValue(locale, fieldName);
    }

    public java.lang.Object getLocalizableDataValue(final String locale, final String fieldName) {
        final Map<String, java.lang.Object> data = this.localizableData.getOrDefault(locale, null);

        if (data == null) {
            return null;
        }

        return data.getOrDefault(fieldName, null);
    }
}
