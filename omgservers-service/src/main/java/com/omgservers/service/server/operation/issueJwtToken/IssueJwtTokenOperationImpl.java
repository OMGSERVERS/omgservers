package com.omgservers.service.server.operation.issueJwtToken;

import com.omgservers.schema.dto.wsToken.WsTokenDto;
import com.omgservers.schema.model.internalRole.InternalRoleEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
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
    private static final Duration WS_TOKEN_DURATION = Duration.ofSeconds(6000);

    final GetConfigOperation getConfigOperation;

    @Override
    public String issueServiceJwtToken() {
        final var issuer = getConfigOperation.getServiceConfig().jwt().issuer();
        final var subject = getConfigOperation.getServiceConfig().index().serverUri().getHost();
        final var jwtToken = Jwt.issuer(issuer)
                .audience(issuer)
                .subject(subject)
                .expiresIn(SERVICE_TOKEN_DURATION)
                .groups(InternalRoleEnum.SERVICE.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueUserJwtToken(final Long userId, final Set<String> groups) {
        final var issuer = getConfigOperation.getServiceConfig().jwt().issuer();
        final var jwtToken = Jwt.issuer(issuer)
                .audience(issuer)
                .subject(userId.toString())
                .expiresIn(USER_TOKEN_DURATION)
                .groups(groups)
                .sign();

        return jwtToken;
    }

    @Override
    public String issueWsJwtToken(final Long clientId,
                                  final Long runtimeId,
                                  final UserRoleEnum role) {
        final var issuer = getConfigOperation.getServiceConfig().jwt().issuer();
        final var jwtToken = Jwt.issuer(issuer)
                .audience(issuer)
                .subject(clientId.toString())
                .claim(WsTokenDto.RUNTIME_ID_CLAIM, runtimeId.toString())
                .claim(WsTokenDto.USER_ROLE_CLAIM, role.getName())
                .expiresIn(WS_TOKEN_DURATION)
                .groups(UserRoleEnum.WEBSOCKET.getName())
                .sign();

        return jwtToken;
    }
}
