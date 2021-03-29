package com.berugo.fastend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Application extends AbstractModel {
    private String defaultLocale;

    @Builder.Default
    private Set<String> availableLocales = new HashSet<>();


}
