package com.omgservers.service.operation.security;

import com.omgservers.service.configuration.WebSocketConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateQuarkusHeaderProtocolOperationImpl implements CreateQuarkusHeaderProtocolOperation {

    @Override
    public String execute(final String wsToken) {
        final var quarkusHeaderProtocol = WebSocketConfiguration.SUB_PROTOCOL +
                ",quarkus-http-upgrade#Authorization#Bearer " + wsToken;
        return quarkusHeaderProtocol;
    }
}
