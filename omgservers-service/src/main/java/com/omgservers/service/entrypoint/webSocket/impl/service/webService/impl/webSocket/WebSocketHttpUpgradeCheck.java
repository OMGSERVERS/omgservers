package com.omgservers.service.entrypoint.webSocket.impl.service.webService.impl.webSocket;

import io.quarkus.websockets.next.HttpUpgradeCheck;
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
public class WebSocketHttpUpgradeCheck implements HttpUpgradeCheck {

    final JWTParser jwtParser;

    @Override
    public boolean appliesTo(final String endpointId) {
        return endpointId.equals(WebSocketEndpoint.class.getName());
    }

    @Override
    public Uni<CheckResult> perform(final HttpUpgradeContext context) {
        log.info("SecurityIdentity, {}", context.securityIdentity());
        final var wsToken = context.httpRequest().getParam("ws_token");
        if (Objects.isNull(wsToken)) {
            log.info("WebSocketEndpoint ws_token query parameter is missing");
            return CheckResult.rejectUpgrade(400);
        }

        try {
            final var jsonWebToken = jwtParser.parse(wsToken);
            final var subject = jsonWebToken.getSubject();
            log.info("Permit webSocket connection, subject={}, groups={}", subject,
                    jsonWebToken.getGroups());
            return CheckResult.permitUpgrade();
        } catch (ParseException e) {
            log.info("WebSocketEndpoint ws_token is invalid, {}:{}", e.getClass().getSimpleName(), e.getMessage());
            return CheckResult.rejectUpgrade(401);
        }
    }
}
