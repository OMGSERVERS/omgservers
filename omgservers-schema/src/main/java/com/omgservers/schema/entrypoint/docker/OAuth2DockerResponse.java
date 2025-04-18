package com.omgservers.schema.entrypoint.docker;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2DockerResponse {

    @JsonProperty("access_token")
    String accessToken;

    String scope;

    @JsonProperty("expires_in")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    Long expiresIn;

    @JsonProperty("issued_at")
    Instant issuedAt;

    @JsonProperty("refresh_token")
    String refreshToken;
}
