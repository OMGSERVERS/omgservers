package com.omgservers.service.operation.security;

import com.omgservers.service.configuration.WebSocketConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateQuarkusHeaderProtocolOperationImpl implements CreateQuarkusHeaderProtocolOperation {

    @Override
    public String execute(final String wsToken) {
        final var quarkusHttpUpgrade = "quarkus-http-upgrade#Authorization#Bearer " + wsToken;
        final var encodedHttpUpgrade = URLEncoder.encode(quarkusHttpUpgrade, StandardCharsets.UTF_8);

        final var quarkusHeaderProtocol = WebSocketConfiguration.SUB_PROTOCOL + "," + encodedHttpUpgrade;
        return quarkusHeaderProtocol;
    }
}
