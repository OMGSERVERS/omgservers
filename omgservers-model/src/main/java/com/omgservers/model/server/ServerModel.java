package com.omgservers.model.server;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long poolId;

    @NotNull
    ServerQualifierEnum qualifier;

    @NotNull
    InetAddress ipAddress;

    @NotNull
    Integer cpuCount;

    @NotNull
    Integer memorySize;

    @NotNull
    ServerConfigModel config;

    @NotNull
    Boolean deleted;
}
