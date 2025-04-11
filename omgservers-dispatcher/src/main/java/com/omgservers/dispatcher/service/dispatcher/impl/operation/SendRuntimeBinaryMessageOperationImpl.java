package com.omgservers.dispatcher.service.dispatcher.impl.operation;

import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatcher;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
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
    public Uni<Boolean> execute(final Dispatcher dispatcher,
                                final List<DispatcherConnection> playerConnections,
                                final String message) {
        final byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(message);
        } catch (IllegalArgumentException e) {
            log.warn("Message from runtime \"{}\" couldn't be decoded, {}:{}",
                    dispatcher.getRuntimeId(),
                    e.getClass().getSimpleName(),
                    e.getMessage());
            return Uni.createFrom().item(Boolean.FALSE);
        }

        return Multi.createFrom().iterable(playerConnections)
                .onItem().transformToUniAndMerge(playerConnection -> playerConnection.sendBytes(bytes)
                        .onFailure().recoverWithUni(t -> {
                            log.warn("Failed to send runtime \"{}\" message to player \"{}\", {}:{}",
                                    dispatcher.getRuntimeId(),
                                    playerConnection.getSubject(),
                                    t.getClass().getSimpleName(),
                                    t.getMessage());
                            //TODO: Trigger client_failed event
                            return Uni.createFrom().voidItem();
                        }))
                .collect().asList()
                .replaceWith(Boolean.TRUE);
    }
}
