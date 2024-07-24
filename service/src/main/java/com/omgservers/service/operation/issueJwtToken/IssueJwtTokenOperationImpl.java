package com.omgservers.service.operation.issueJwtToken;

import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.model.wsToken.WsToken;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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
    private static final String TOKEN_ISSUER = "https://omgservers.com";
    private static final Duration ADMIN_TOKEN_DURATION = Duration.ofSeconds(3600);
    private static final Duration SERVICE_TOKEN_DURATION = Duration.ofSeconds(31536000);
    private static final Duration ROUTER_TOKEN_DURATION = Duration.ofSeconds(31536000);
    private static final Duration USER_TOKEN_DURATION = Duration.ofSeconds(3600);
    private static final Duration WS_TOKEN_DURATION = Duration.ofSeconds(6000);

    final GetConfigOperation getConfigOperation;

    @Override
    public String issueAdminJwtToken() {
        final var subject = String.valueOf(getConfigOperation.getServiceConfig().defaults().adminId());
        final var jwtToken = Jwt.issuer(TOKEN_ISSUER)
                .subject(subject)
                .expiresIn(ADMIN_TOKEN_DURATION)
                .groups(UserRoleEnum.ADMIN.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueServiceJwtToken() {
        final var subject = getConfigOperation.getServiceConfig().index().serverUri().getHost();
        final var jwtToken = Jwt.issuer(TOKEN_ISSUER)
                .subject(subject)
                .expiresIn(SERVICE_TOKEN_DURATION)
                .groups(InternalRoleEnum.SERVICE.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueRouterUserJwtToken() {
        final var subject = String.valueOf(getConfigOperation.getServiceConfig().defaults().routerUserId());
        final var jwtToken = Jwt.issuer(TOKEN_ISSUER)
                .subject(subject)
                .expiresIn(ROUTER_TOKEN_DURATION)
                .groups(UserRoleEnum.ROUTER.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueUserJwtToken(final Long userId, final Set<String> groups) {
        final var jwtToken = Jwt.issuer(TOKEN_ISSUER)
                .subject(userId.toString())
                .expiresIn(USER_TOKEN_DURATION)
                .groups(groups)
                .sign();

        return jwtToken;
    }

    public String issueWsJwtToken(final Long clientId,
                                  final Long runtimeId,
                                  final UserRoleEnum role) {
        final var jwtToken = Jwt.issuer(TOKEN_ISSUER)
                .subject(clientId.toString())
                .claim(WsToken.RUNTIME_ID_CLAIM, runtimeId.toString())
                .claim(WsToken.USER_ROLE_CLAIM, role.getName())
                .expiresIn(WS_TOKEN_DURATION)
                .groups(UserRoleEnum.WEBSOCKET.getName())
                .sign();

        return jwtToken;
    }
}
