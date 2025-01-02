package com.omgservers.dispatcher.service.room.impl.operation;

import com.omgservers.dispatcher.service.dispatcher.component.DispatcherConnection;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SendRuntimeTextMessageOperationImpl implements SendRuntimeTextMessageOperation {

    @Override
    public Uni<Boolean> execute(final DispatcherRoom room,
                                final List<DispatcherConnection> playerConnections,
                                final String message) {
        return Multi.createFrom().iterable(playerConnections)
                .onItem().transformToUniAndMerge(playerConnection -> playerConnection.sendText(message)
                        .onFailure().recoverWithUni(t -> {
                            log.warn("Failed to send outgoing runtime text message, " +
                                            "room={}, playerConnection={}, {}:{}",
                                    room, playerConnection, t.getClass().getSimpleName(), t.getMessage());
                            //TODO: Trigger client_failed event
                            return Uni.createFrom().voidItem();
                        }))
                .collect().asList()
                .replaceWith(Boolean.TRUE);
    }
}
