package com.berugo.fastend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
public class Client {

    @Id
    @RestResource(exported = false)
    private String id;

    private String externalId;

    private String applicationId;
}
