package com.berugo.fastend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractModel {
    @Id
    @RestResource(exported = false)
    private String id;

    private String externalId;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @Builder.Default
    private boolean disabled = false;
}
