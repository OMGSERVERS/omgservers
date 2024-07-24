package com.omgservers.service.entrypoint.player.impl.service.webService.impl.playerWebSocket;

import io.quarkus.security.identity.SecurityIdentity;
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
public class PlayerHttpUpgradeCheck implements HttpUpgradeCheck {

    final JWTParser jwtParser;

    @Override
    public boolean appliesTo(String endpointId) {
        return endpointId.equals(PlayerWebSocket.class.getName());
    }

    @Override
    public Uni<CheckResult> perform(HttpUpgradeContext context) {
        log.info("SecurityIdentity, {}", context.securityIdentity());
        final var wsToken = context.httpRequest().getParam("ws_token");
        if (Objects.isNull(wsToken)) {
            log.info("WebSocketEndpoint ws_token query parameter is missing");
            return CheckResult.rejectUpgrade(400);
        }

        try {
            final var jsonWebToken = jwtParser.parse(wsToken);
            final var subject = jsonWebToken.getSubject();

            final var audienceOptional = jsonWebToken.getAudience().stream().findFirst();
            if (audienceOptional.isEmpty()) {
                log.info("WebSocketEndpoint ws_token audience claim is missing");
                return CheckResult.rejectUpgrade(400);
            }
            final var audience = audienceOptional.get();

            log.info("Permit webSocket upgrade, subject={}, audience={}, groups={}", subject, audience,
                    jsonWebToken.getGroups());
            return CheckResult.permitUpgrade();
        } catch (ParseException e) {
            log.info("WebSocketEndpoint ws_token is invalid, {}:{}", e.getClass().getSimpleName(), e.getMessage());
            return CheckResult.rejectUpgrade(401);
        }
    }
}
