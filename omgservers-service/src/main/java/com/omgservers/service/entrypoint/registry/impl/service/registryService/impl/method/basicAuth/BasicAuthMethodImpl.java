package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.basicAuth;

import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.parseBasicAuthorizationHeader.ParseBasicAuthorizationHeaderOperation;
import com.omgservers.service.service.registry.RegistryService;
import com.omgservers.service.service.registry.dto.IssueTokenRequest;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BasicAuthMethodImpl implements BasicAuthMethod {

    final UserModule userModule;

    final RegistryService registryService;

    final ParseBasicAuthorizationHeaderOperation parseBasicAuthorizationHeaderOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<BasicAuthRegistryResponse> basicAuth(final BasicAuthRegistryRequest request) {
        log.debug("Requested, {}", request);

        final var authorizationHeader = request.getAuthorizationHeader();
        if (Objects.isNull(authorizationHeader)) {
            throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_CREDENTIALS);
        }

        try {
            final var userCredentials = parseBasicAuthorizationHeaderOperation
                    .parseBasicAuthorizationHeader(authorizationHeader);

            final var userId = userCredentials.getUserId();
            final var password = userCredentials.getPassword();

            return createToken(userId, password)
                    .flatMap(rawToken -> {
                        final var offlineToken = Objects.nonNull(request.getOfflineToken()) ?
                                request.getOfflineToken() : Boolean.FALSE;
                        final var scope = request.getScope();
                        final var issueTokenRequest = new IssueTokenRequest(userId, offlineToken, scope);
                        return registryService.issueToken(issueTokenRequest)
                                .map(getTokenResponse -> {
                                    final var response = new BasicAuthRegistryResponse();
                                    response.setToken(getTokenResponse.getAccessToken());
                                    response.setAccessToken(getTokenResponse.getAccessToken());
                                    response.setExpiresIn(getTokenResponse.getExpiresIn());
                                    response.setIssuedAt(getTokenResponse.getIssuedAt());
                                    // If server returns refresh token, docker registry uses OAuth2 refresh_token grant type
                                    response.setRefreshToken(getTokenResponse.getRefreshToken());
                                    return response;
                                });
                    });
        } catch (ServerSideBadRequestException e) {
            throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_CREDENTIALS, e.getMessage(), e);
        }
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken)
                .onFailure(ServerSideNotFoundException.class)
                .transform(t -> new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_CREDENTIALS,
                        t.getMessage(), t));
    }
}
