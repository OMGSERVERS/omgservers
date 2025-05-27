package com.omgservers.schema.model.deploymentLobbyResource;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentLobbyResourceModel {

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
    Long lobbyId;

    @NotNull
    DeploymentLobbyResourceStatusEnum status;

    @Valid
    @NotNull
    DeploymentLobbyResourceConfigDto config;

    @NotNull
    Boolean deleted;
}
