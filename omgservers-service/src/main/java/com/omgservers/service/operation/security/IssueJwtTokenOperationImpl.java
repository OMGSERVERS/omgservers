package com.omgservers.service.operation.security;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IssueJwtTokenOperationImpl implements IssueJwtTokenOperation {

    // TODO: get from configuration
    private static final Duration SERVICE_TOKEN_DURATION = Duration.ofSeconds(31536000);
    private static final Duration USER_TOKEN_DURATION = Duration.ofSeconds(3600);
    private static final Duration RUNTIME_TOKEN_DURATION = Duration.ofSeconds(3600);
    private static final Duration WS_TOKEN_DURATION = Duration.ofSeconds(3600);

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public String issueServiceJwtToken() {
        final var issuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var subject = getServiceConfigOperation.getServiceConfig().shard().uri().getHost();
        final var jwtToken = Jwt.issuer(issuer)
                .audience(issuer)
                .subject(subject)
                .expiresIn(SERVICE_TOKEN_DURATION)
                .groups(UserRoleEnum.SERVICE.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueUserJwtToken(final Long userId, final Set<String> groups) {
        final var issuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var jwtToken = Jwt.issuer(issuer)
                .audience(issuer)
                .subject(userId.toString())
                .claim(SecurityAttributesEnum.USER_ID.getAttributeName(), userId.toString())
                .expiresIn(USER_TOKEN_DURATION)
                .groups(groups)
                .sign();

        return jwtToken;
    }

    @Override
    public String issueRuntimeJwtToken(final Long runtimeId) {
        final var issuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var jwtToken = Jwt.issuer(issuer)
                .audience(issuer)
                .subject(runtimeId.toString())
                .claim(SecurityAttributesEnum.RUNTIME_ID.getAttributeName(), runtimeId.toString())
                .expiresIn(RUNTIME_TOKEN_DURATION)
                .groups(UserRoleEnum.RUNTIME.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueWsJwtToken(final Long subject,
                                  final Long runtimeId,
                                  final UserRoleEnum role) {
        final var issuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var jwtToken = Jwt.issuer(issuer)
                .audience(issuer)
                .subject(subject.toString())
                .claim(SecurityAttributesEnum.RUNTIME_ID.getAttributeName(), runtimeId.toString())
                .claim(SecurityAttributesEnum.USER_ROLE.getAttributeName(), role.getName())
                .expiresIn(WS_TOKEN_DURATION)
                .groups(UserRoleEnum.WEBSOCKET.getName())
                .sign();

        return jwtToken;
    }
}
