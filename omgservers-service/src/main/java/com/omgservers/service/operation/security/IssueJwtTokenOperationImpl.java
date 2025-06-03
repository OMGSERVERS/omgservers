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

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public String issueServiceJwtToken() {
        final var jwtIssuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var serviceAudience = getServiceConfigOperation.getServiceConfig().jwt().audience().service();
        final var expiresIn = getServiceConfigOperation.getServiceConfig().jwt().expiresIn().service();

        final var subject = getServiceConfigOperation.getServiceConfig().shard().uri().getHost();
        final var jwtToken = Jwt.issuer(jwtIssuer)
                .audience(serviceAudience)
                .subject(subject)
                .expiresIn(Duration.ofSeconds(expiresIn))
                .groups(UserRoleEnum.SERVICE.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueUserJwtToken(final Long userId, final Set<String> groups) {
        final var jwtIssuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var serviceAudience = getServiceConfigOperation.getServiceConfig().jwt().audience().service();
        final var expiresIn = getServiceConfigOperation.getServiceConfig().jwt().expiresIn().user();

        final var jwtToken = Jwt.issuer(jwtIssuer)
                .audience(serviceAudience)
                .subject(userId.toString())
                .claim(SecurityAttributesEnum.USER_ID.getAttributeName(), userId.toString())
                .expiresIn(Duration.ofSeconds(expiresIn))
                .groups(groups)
                .sign();

        return jwtToken;
    }

    @Override
    public String issueRuntimeJwtToken(final Long runtimeId) {
        final var jwtIssuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var serviceAudience = getServiceConfigOperation.getServiceConfig().jwt().audience().service();
        final var expiresIn = getServiceConfigOperation.getServiceConfig().jwt().expiresIn().runtime();

        final var jwtToken = Jwt.issuer(jwtIssuer)
                .audience(serviceAudience)
                .subject(runtimeId.toString())
                .claim(SecurityAttributesEnum.RUNTIME_ID.getAttributeName(), runtimeId.toString())
                .expiresIn(Duration.ofSeconds(expiresIn))
                .groups(UserRoleEnum.RUNTIME.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueConnectorClientWsToken(final Long clientId) {
        final var jwtIssuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var connectorAudience = getServiceConfigOperation.getServiceConfig().jwt().audience().connector();
        final var wsTokenExpires = getServiceConfigOperation.getServiceConfig().jwt().expiresIn().wsToken();

        final var jwtToken = Jwt.issuer(jwtIssuer)
                .audience(connectorAudience)
                .subject(clientId.toString())
                .claim(SecurityAttributesEnum.USER_ROLE.getAttributeName(), UserRoleEnum.PLAYER.getName())
                .claim(SecurityAttributesEnum.CLIENT_ID.getAttributeName(), clientId.toString())
                .expiresIn(Duration.ofSeconds(wsTokenExpires))
                .groups(UserRoleEnum.WEBSOCKET.getName())
                .sign();

        return jwtToken;
    }

    @Override
    public String issueDispatcherClientWsToken(final Long subject,
                                               final Long runtimeId,
                                               final UserRoleEnum role) {
        final var jwtIssuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
        final var dispatcherAudience = getServiceConfigOperation.getServiceConfig().jwt().audience().dispatcher();
        final var wsTokenExpires = getServiceConfigOperation.getServiceConfig().jwt().expiresIn().wsToken();

        final var jwtToken = Jwt.issuer(jwtIssuer)
                .audience(dispatcherAudience)
                .subject(subject.toString())
                .claim(SecurityAttributesEnum.RUNTIME_ID.getAttributeName(), runtimeId.toString())
                .claim(SecurityAttributesEnum.USER_ROLE.getAttributeName(), role.getName())
                .expiresIn(Duration.ofSeconds(wsTokenExpires))
                .groups(UserRoleEnum.WEBSOCKET.getName())
                .sign();

        return jwtToken;
    }
}
