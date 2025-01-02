package com.omgservers.dispatcher.operation;

import com.omgservers.dispatcher.security.DispatcherHeadersEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDispatcherHeaderOperationImpl implements GetDispatcherHeaderOperation {

    @Override
    public Long getRuntimeId(final WebSocketConnection webSocketConnection) {
        final var handshakeRequest = webSocketConnection.handshakeRequest();
        final var headerValue = handshakeRequest.header(DispatcherHeadersEnum.RUNTIME_ID.getHeaderName());
        if (Objects.nonNull(headerValue)) {
            final var runtimeId = Long.valueOf(headerValue);
            return runtimeId;
        } else {
            return null;
        }
    }

    @Override
    public UserRoleEnum getUserRole(final WebSocketConnection webSocketConnection) {
        final var handshakeRequest = webSocketConnection.handshakeRequest();
        final var headerValue = handshakeRequest.header(DispatcherHeadersEnum.USER_ROLE.getHeaderName());
        if (Objects.nonNull(headerValue)) {
            final var userRole = UserRoleEnum.valueOf(headerValue);
            return userRole;
        } else {
            return null;
        }
    }

    @Override
    public Long getSubject(final WebSocketConnection webSocketConnection) {
        final var handshakeRequest = webSocketConnection.handshakeRequest();
        final var headerValue = handshakeRequest.header(DispatcherHeadersEnum.SUBJECT.getHeaderName());
        if (Objects.nonNull(headerValue)) {
            final var subject = Long.valueOf(headerValue);
            return subject;
        } else {
            return null;
        }
    }
}
