package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionResponse;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatchers;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RemovePlayerConnectionMethodImpl implements RemovePlayerConnectionMethod {

    final Dispatchers dispatchers;

    @Override
    public Uni<RemovePlayerConnectionResponse> execute(final RemovePlayerConnectionRequest request) {
        log.debug("{}", request);

        final var playerConnection = request.getPlayerConnection();
        final var subject = playerConnection.getSubject();
        final var runtimeId = playerConnection.getRuntimeId();

        final var dispatcher = dispatchers.findDispatcher(playerConnection);
        if (Objects.nonNull(dispatcher)) {
            final var removed = dispatcher.remove(playerConnection);
            if (removed) {
                log.info("Player \"{}\" removed from the dispatcher \"{}\"", subject, runtimeId);
                return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.TRUE));
            } else {
                log.warn("Player \"{}\" not found to be removed from dispatcher \"{}\"", subject, runtimeId);
                return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.FALSE));
            }
        } else {
            log.warn("Dispatcher \"{}\" not found to remove player \"{}\"", runtimeId, subject);
            return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.FALSE));
        }
    }
}
