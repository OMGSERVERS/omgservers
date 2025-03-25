package com.omgservers.service.service.cache.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetClientLastActivityRequest {

    @NotNull
    Long clientId;

    @NotNull
    Instant lastActivity;
}
