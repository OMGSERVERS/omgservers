package com.omgservers.schema.service.registry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueTokenResponse {

    String accessToken;
    String scope;
    Long expiresIn;
    Instant issuedAt;
    String refreshToken;
}
