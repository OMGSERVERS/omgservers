package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.oAuth2;

import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.service.operation.parseBasicAuthorizationHeader.ParseBasicAuthorizationHeaderOperation;
import com.omgservers.service.service.registry.RegistryService;
import com.omgservers.service.service.registry.dto.IssueTokenRequest;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class OAuth2MethodImpl implements OAuth2Method {

    final RegistryService registryService;

    final ParseBasicAuthorizationHeaderOperation parseBasicAuthorizationHeaderOperation;

    final SecurityIdentity securityIdentity;
    final JWTParser jwtParser;

    @Override
    public Uni<OAuth2RegistryResponse> oAuth2(final OAuth2RegistryRequest request) {
        log.debug("Requested, {}", request);

        final var grantType = request.getGrantType();
        if (grantType.equals("refresh_token")) {
            final var refreshToken = request.getRefreshToken();
            if (Objects.isNull(refreshToken)) {
                throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_REQUEST);
            }

            try {
                final var jsonWebToken = jwtParser.parse(refreshToken);
                final var userId = Long.valueOf(jsonWebToken.getSubject());

                final var scope = request.getScope();
                final var issueTokenRequest = new IssueTokenRequest(userId, Boolean.FALSE, scope);
                return registryService.issueToken(issueTokenRequest)
                        .map(getTokenResponse -> {
                            final var response = new OAuth2RegistryResponse();
                            response.setAccessToken(getTokenResponse.getAccessToken());
                            response.setScope(getTokenResponse.getScope());
                            response.setExpiresIn(getTokenResponse.getExpiresIn());
                            response.setIssuedAt(getTokenResponse.getIssuedAt());
                            // Return the same refresh token
                            response.setRefreshToken(refreshToken);
                            return response;
                        });

            } catch (ParseException | NumberFormatException e) {
                throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_REQUEST, e.getMessage(), e);
            }

        } else {
            throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_REQUEST,
                    "unsupported grant type, type=" + grantType);
        }
    }
}
