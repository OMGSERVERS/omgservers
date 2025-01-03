package com.omgservers.schema.entrypoint.docker;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.reactive.RestForm;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2DockerRequest {

    @NotNull
    @RestForm("grant_type")
    String grantType;

    @NotNull
    @RestForm
    String service;

    @NotNull
    @RestForm("client_id")
    String clientId;

    @RestForm("access_type")
    String accessType;

    @RestForm("scope")
    String scope;

    @RestForm("refresh_token")
    String refreshToken;

    @RestForm("username")
    String username;

    @RestForm("password")
    String password;
}