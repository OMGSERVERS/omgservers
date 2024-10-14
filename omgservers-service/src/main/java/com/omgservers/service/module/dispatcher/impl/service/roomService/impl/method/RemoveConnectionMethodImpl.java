package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomsContainer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RemoveConnectionMethodImpl implements RemoveConnectionMethod {

    final RoomsContainer roomsContainer;

    @Override
    public Uni<Void> execute(final RemoveConnectionRequest request) {
        log.debug("Remove connection, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var roomInstance = roomsContainer.findRoom(webSocketConnection);
        roomInstance.ifPresent(instance -> instance.removeConnection(webSocketConnection));

        return Uni.createFrom().voidItem();
    }
}
