package com.omgservers.service.server.registry.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRegistryTokensRequest {

    @NotNull
    Long userId;

    @NotNull
    Boolean offlineToken;

    String requestedScope;
}
