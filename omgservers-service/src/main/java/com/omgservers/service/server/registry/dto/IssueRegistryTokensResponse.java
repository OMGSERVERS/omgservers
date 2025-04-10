package com.omgservers.service.server.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRegistryTokensResponse {

    String accessToken;
    String scope;
    Long expiresIn;
    Instant issuedAt;
    String refreshToken;
}
