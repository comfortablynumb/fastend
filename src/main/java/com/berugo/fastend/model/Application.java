package com.berugo.fastend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Application {

    @Id
    private String id;

    private String externalId;
}
