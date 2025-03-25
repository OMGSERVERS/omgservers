package com.omgservers.schema.model.deploymentCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = DeploymentCommandDeserializer.class)
public class DeploymentCommandModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long deploymentId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    DeploymentCommandQualifierEnum qualifier;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    DeploymentCommandBodyDto body;

    @NotNull
    Boolean deleted;
}
