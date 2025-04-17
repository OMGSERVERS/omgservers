package com.omgservers.service.server.cache.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCachedRuntimeLastActivityRequest {

    @NotNull
    Long runtimeId;
}
