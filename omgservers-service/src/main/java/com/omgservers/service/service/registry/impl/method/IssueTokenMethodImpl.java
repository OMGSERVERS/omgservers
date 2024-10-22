package com.omgservers.service.service.registry.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.service.registry.dto.DockerRegistryAccessDto;
import com.omgservers.service.service.registry.dto.IssueTokenRequest;
import com.omgservers.service.service.registry.dto.IssueTokenResponse;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.service.registry.operation.intersectDockerRegistryScope.IntersectDockerRegistryScopeOperation;
import com.omgservers.service.service.registry.operation.issueDockerRegistryClientAccessToken.IssueDockerRegistryClientAccessTokenOperation;
import com.omgservers.service.service.registry.operation.issueDockerRegistryClientRefreshToken.IssueDockerRegistryClientRefreshTokenOperation;
import com.omgservers.service.service.registry.operation.parseDockerRegistryScope.ParseDockerRegistryScopeOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IssueTokenMethodImpl implements IssueTokenMethod {

    final UserModule userModule;

    final IssueDockerRegistryClientRefreshTokenOperation issueDockerRegistryClientRefreshTokenOperation;
    final IssueDockerRegistryClientAccessTokenOperation issueDockerRegistryClientAccessTokenOperation;
    final IntersectDockerRegistryScopeOperation intersectDockerRegistryScopeOperation;
    final ParseDockerRegistryScopeOperation parseDockerRegistryScopeOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<IssueTokenResponse> issueToken(final IssueTokenRequest request) {
        log.debug("Requested, {}", request);

        final var userId = request.getUserId();

        final var requestedScope = request.getRequestedScope();
        final var dockerRegistryScope = parseDockerRegistryScopeOperation
                .parseDockerRegistryScope(requestedScope);

        return intersectDockerRegistryScopeOperation.intersectDockerRegistryScope(userId,
                        dockerRegistryScope)
                .map(intersection -> {
                    final var accessToken = issueDockerRegistryClientAccessTokenOperation
                            .issueDockerRegistryClientAccessToken(userId, intersection);

                    final var expiresIn = accessToken.getExpirationTime() - accessToken.getIssuedAtTime();

                    final var scope = intersection.stream()
                            .map(DockerRegistryAccessDto::toString).collect(Collectors.joining(" "));

                    final var response = new IssueTokenResponse();
                    response.setAccessToken(accessToken.getRawToken());
                    response.setScope(scope);
                    response.setExpiresIn(expiresIn);
                    response.setIssuedAt(Instant.ofEpochSecond(accessToken.getIssuedAtTime()));

                    final var offlineToken = request.getOfflineToken();
                    if (offlineToken) {
                        final var refreshToken = issueDockerRegistryClientRefreshTokenOperation
                                .issueDockerRegistryClientRefreshToken(userId);
                        response.setRefreshToken(refreshToken.getRawToken());
                    }

                    final var tokenAccess = accessToken.getClaim("access");
                    log.info("Docker registry client access token was issued, userId={}, access={}, offlineToken={}",
                            userId, tokenAccess, offlineToken);

                    return response;
                });
    }
}
