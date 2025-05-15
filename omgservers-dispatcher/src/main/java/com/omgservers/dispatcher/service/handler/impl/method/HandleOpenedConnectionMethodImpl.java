package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.dispatcher.DispatcherService;
import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionResponse;
import com.omgservers.dispatcher.service.dispatcher.dto.CreateDispatcherRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.CreateDispatcherResponse;
import com.omgservers.dispatcher.service.handler.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.dispatcher.service.handler.impl.components.DispatcherConnections;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleOpenedConnectionMethodImpl implements HandleOpenedConnectionMethod {

    final DispatcherService dispatcherService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleOpenedConnectionRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var runtimeId = request.getRuntimeId();
        final var userRole = request.getUserRole();
        final var subject = request.getSubject();

        log.info("Opening websocket \"{}\", runtimeId=\"{}\", userRole=\"{}\", subject=\"{}\"",
                webSocketConnection.id(),
                runtimeId,
                userRole,
                subject);

        final var dispatcherConnection = new DispatcherConnection(webSocketConnection,
                runtimeId,
                userRole,
                subject);

        return handleDispatcherConnection(dispatcherConnection)
                .invoke(handled -> dispatcherConnections.put(dispatcherConnection))
                .flatMap(result -> {
                    if (!result) {
                        log.error("Closing websocket \"{}\", failed to open connection", webSocketConnection.id());
                        return webSocketConnection.close(DispatcherCloseReason.FAILED_TO_OPEN);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Boolean> handleDispatcherConnection(final DispatcherConnection dispatcherConnection) {
        final var userRole = dispatcherConnection.getUserRole();

        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var createDispatcherRequest = new CreateDispatcherRequest(dispatcherConnection);
            return dispatcherService.execute(createDispatcherRequest)
                    .map(CreateDispatcherResponse::getCreated);
        } else {
            final var request = new AddPlayerConnectionRequest(dispatcherConnection);
            return dispatcherService.execute(request)
                    .map(AddPlayerConnectionResponse::getAdded);
        }
    }
}
