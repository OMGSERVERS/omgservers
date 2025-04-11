package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.DeleteDispatcherRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.DeleteDispatcherResponse;
import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatchers;
import com.omgservers.dispatcher.service.handler.component.DispatcherCloseReason;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDispatcherMethodImpl implements DeleteDispatcherMethod {

    final Dispatchers dispatchers;

    @Override
    public Uni<DeleteDispatcherResponse> execute(final DeleteDispatcherRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();

        final var dispatcher = dispatchers.deleteDispatcher(runtimeId);
        if (Objects.nonNull(dispatcher)) {
            final var allPlayerConnections = dispatcher.getAllPlayerConnections();

            if (allPlayerConnections.isEmpty()) {
                log.info("Dispatcher \"{}\" removed. There are no currently connected players.", runtimeId);
            } else {
                log.warn("Dispatcher \"{}\" removed. \"{}\" players are still connected.",
                        runtimeId, allPlayerConnections.size());
            }
            return Multi.createFrom().iterable(allPlayerConnections)
                    .onItem().transformToUniAndConcatenate(playerConnection -> {
                        final var webSocketConnection = playerConnection.getWebSocketConnection();
                        if (webSocketConnection.isOpen()) {
                            return webSocketConnection.close(DispatcherCloseReason.DISPATCHER_DELETED);
                        } else {
                            return Uni.createFrom().voidItem();
                        }
                    })
                    .collect().asList()
                    .replaceWith(new DeleteDispatcherResponse(Boolean.TRUE));
        } else {
            log.error("Dispatcher \"{}\" not found to be removed", runtimeId);
            return Uni.createFrom().item(new DeleteDispatcherResponse(Boolean.FALSE));
        }
    }
}
