package com.berugo.fastend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Client extends AbstractModel {
    private String applicationId;
}
