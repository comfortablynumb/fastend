package com.berugo.quickend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Application extends AbstractModel {
    private String defaultLocale;

    @Builder.Default
    @Type(type = "json")
    @Column(columnDefinition = "VARCHAR(1000)")
    private Set<String> availableLocales = new HashSet<>();

}
