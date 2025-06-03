package com.omgservers.dispatcher.server.dispatcher.impl.operation;

import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatcher;
import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
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
    public Uni<Boolean> execute(final Dispatcher dispatcher,
                                final List<DispatcherConnection> playerConnections,
                                final String message) {
        return Multi.createFrom().iterable(playerConnections)
                .onItem().transformToUniAndMerge(playerConnection -> playerConnection.sendText(message)
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
