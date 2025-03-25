package com.omgservers.service.entrypoint.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.service.operation.alias.GetIdByUserOperation;
import com.omgservers.service.operation.security.ParseBasicAuthorizationHeaderOperation;
import com.omgservers.service.service.registry.RegistryService;
import com.omgservers.service.service.registry.dto.IssueRegistryTokensRequest;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.user.UserShard;
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

    final AliasShard aliasShard;
    final UserShard userShard;

    final RegistryService registryService;

    final ParseBasicAuthorizationHeaderOperation parseBasicAuthorizationHeaderOperation;
    final GetIdByUserOperation getIdByUserOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<BasicAuthDockerResponse> basicAuth(final BasicAuthDockerRequest request) {
        log.info("Requested, {}", request);

        final var authorizationHeader = request.getAuthorizationHeader();
        if (Objects.isNull(authorizationHeader)) {
            throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_CREDENTIALS);
        }

        try {
            final var userCredentials = parseBasicAuthorizationHeaderOperation
                    .parseBasicAuthorizationHeader(authorizationHeader);

            final var user = userCredentials.getUser();
            final var password = userCredentials.getPassword();

            return getIdByUserOperation.execute(user)
                    .flatMap(userId -> createToken(userId, password)
                            .flatMap(rawToken -> {
                                final var offlineToken = Objects.nonNull(request.getOfflineToken()) ?
                                        request.getOfflineToken() : Boolean.FALSE;
                                final var scope = request.getScope();
                                final var issueTokenRequest = new IssueRegistryTokensRequest(userId, offlineToken, scope);
                                return registryService.execute(issueTokenRequest)
                                        .map(getTokenResponse -> {
                                            final var response = new BasicAuthDockerResponse();
                                            response.setToken(getTokenResponse.getAccessToken());
                                            response.setAccessToken(getTokenResponse.getAccessToken());
                                            response.setExpiresIn(getTokenResponse.getExpiresIn());
                                            response.setIssuedAt(getTokenResponse.getIssuedAt());
                                            // If server returns refresh token, docker registry uses OAuth2 refresh_token grant type
                                            response.setRefreshToken(getTokenResponse.getRefreshToken());

                                            log.info("Registry tokens issued for account=\"{}\" with scope=\"{}\"",
                                                    request.getAccount(), getTokenResponse.getScope());

                                            return response;
                                        });
                            }));
        } catch (ServerSideBadRequestException e) {
            throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_CREDENTIALS, e.getMessage(), e);
        }
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userShard.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken)
                .onFailure(ServerSideNotFoundException.class)
                .transform(t -> new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_CREDENTIALS,
                        t.getMessage(), t));
    }
}
