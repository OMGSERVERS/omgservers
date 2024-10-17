package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SendRuntimeBinaryMessageOperationImpl implements SendRuntimeBinaryMessageOperation {

    @Override
    public Uni<Boolean> execute(final DispatcherRoom room,
                                final List<DispatcherConnection> playerConnections,
                                final String message) {
        final byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(message);
        } catch (IllegalArgumentException e) {
            log.warn("Wrong outgoing runtime message was received, " +
                            "message couldn't be decoded, room={}, {}:{}",
                    room, e.getClass().getSimpleName(), e.getMessage());
            return Uni.createFrom().item(Boolean.FALSE);
        }

        return Multi.createFrom().iterable(playerConnections)
                .onItem().transformToUniAndMerge(playerConnection -> playerConnection.sendBytes(bytes)
                        .onFailure().recoverWithUni(t -> {
                            log.warn("Failed to send outgoing runtime binary message, " +
                                            "room={}, playerConnection={}, {}:{}",
                                    room, playerConnection, t.getClass().getSimpleName(), t.getMessage());
                            //TODO: Trigger client_failed event
                            return Uni.createFrom().voidItem();
                        }))
                .collect().asList()
                .replaceWith(Boolean.TRUE);
    }
}
