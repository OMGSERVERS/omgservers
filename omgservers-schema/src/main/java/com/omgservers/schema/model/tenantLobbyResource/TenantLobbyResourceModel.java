package com.omgservers.schema.model.tenantLobbyResource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantLobbyResourceModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

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

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long lobbyId;

    @NotNull
    TenantLobbyResourceStatusEnum status;

    @NotNull
    Boolean deleted;
}
