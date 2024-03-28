package com.omgservers.model.serverContainer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerContainerModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Long serverId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    String image;

    @NotNull
    Integer cpuLimit;

    @NotNull
    Integer memoryLimit;

    @NotNull
    ServerContainerConfigModel config;

    @NotNull
    Boolean deleted;
}
