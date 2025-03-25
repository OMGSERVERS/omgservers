package com.omgservers.service.service.registry.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.service.registry.dto.IssueRegistryTokensRequest;
import com.omgservers.service.service.registry.dto.IssueRegistryTokensResponse;
import com.omgservers.service.service.registry.dto.ParsedResourceScope;
import com.omgservers.service.service.registry.dto.RegistryResourceAccess;
import com.omgservers.service.service.registry.operation.IntersectScopeAndPermissionsOperation;
import com.omgservers.service.service.registry.operation.IssueRegistryAccessTokenOperation;
import com.omgservers.service.service.registry.operation.IssueRegistryRefreshTokenOperation;
import com.omgservers.service.service.registry.operation.ParseResourceScopeOperation;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IssueRegistryTokensMethodImpl implements IssueRegistryTokensMethod {

    final UserShard userShard;

    final IssueRegistryRefreshTokenOperation issueRegistryRefreshTokenOperation;
    final IssueRegistryAccessTokenOperation issueRegistryAccessTokenOperation;
    final IntersectScopeAndPermissionsOperation intersectScopeAndPermissionsOperation;

    final ParseResourceScopeOperation parseResourceScopeOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<IssueRegistryTokensResponse> execute(final IssueRegistryTokensRequest request) {
        log.trace("{}", request);

        final var userId = request.getUserId();

        final var requestedScope = request.getRequestedScope();
        final var parsedScope = parseRequestedScope(requestedScope);

        return intersectScopeAndPermissionsOperation.execute(userId, parsedScope)
                .map(intersection -> {
                    final var accessToken = issueRegistryAccessTokenOperation
                            .execute(userId, intersection);

                    final var expiresIn = accessToken.getExpirationTime() - accessToken.getIssuedAtTime();

                    final var scope = intersection.stream()
                            .map(RegistryResourceAccess::buildScopeString)
                            .collect(Collectors.joining(" "));

                    final var response = new IssueRegistryTokensResponse();
                    response.setAccessToken(accessToken.getRawToken());
                    response.setScope(scope);
                    response.setExpiresIn(expiresIn);
                    response.setIssuedAt(Instant.ofEpochSecond(accessToken.getIssuedAtTime()));

                    final var offlineToken = request.getOfflineToken();
                    if (offlineToken) {
                        final var refreshToken = issueRegistryRefreshTokenOperation
                                .issueDockerRegistryClientRefreshToken(userId);
                        response.setRefreshToken(refreshToken.getRawToken());
                    }

                    final var tokenAccess = accessToken.getClaim("access");
                    log.debug("Docker registry client access token was issued, userId={}, access={}, offlineToken={}",
                            userId, tokenAccess, offlineToken);

                    return response;
                });
    }

    List<ParsedResourceScope> parseRequestedScope(final String requestedScope) {
        if (Objects.isNull(requestedScope)) {
            return List.of();
        } else {
            return Stream.of(requestedScope.split(" "))
                    .map(resourceScope -> {
                        try {
                            return parseResourceScopeOperation.execute(resourceScope);
                        } catch (ServerSideBadRequestException e) {
                            log.warn("Scope \"{}\" is invalid, skipping, {}", resourceScope, e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }
    }
}
