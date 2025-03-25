package com.omgservers.service.service.cache.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetRuntimeLastActivityRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    Instant lastActivity;
}
