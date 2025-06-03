package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.dispatcher.DispatcherService;
import com.omgservers.dispatcher.server.dispatcher.dto.DeleteDispatcherRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.DeleteDispatcherResponse;
import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionResponse;
import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.server.handler.dto.HandleClosedConnectionRequest;
import com.omgservers.dispatcher.server.handler.impl.components.DispatcherConnections;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleClosedConnectionMethodImpl implements HandleClosedConnectionMethod {

    final DispatcherService dispatcherService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleClosedConnectionRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return handleDispatcherConnection(dispatcherConnection)
                    .replaceWithVoid();
        } else {
            log.error("Dispatcher connection was not found, skip operation for \"{}\"", webSocketConnection.id());
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> handleDispatcherConnection(final DispatcherConnection dispatcherConnection) {
        final var userRole = dispatcherConnection.getUserRole();

        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var runtimeId = dispatcherConnection.getRuntimeId();

            final var request = new DeleteDispatcherRequest(runtimeId);
            return dispatcherService.execute(request)
                    .map(DeleteDispatcherResponse::getDeleted);
        } else {
            final var request = new RemovePlayerConnectionRequest(dispatcherConnection);
            return dispatcherService.execute(request)
                    .map(RemovePlayerConnectionResponse::getRemoved);
        }
    }
}
