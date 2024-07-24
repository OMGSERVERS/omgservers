package com.omgservers.service.service.room.impl.method.handleBinaryMessage;

import com.omgservers.service.service.room.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    @Override
    public Uni<Void> handleBinaryMessage(final HandleBinaryMessageRequest request) {
        log.debug("Handle binary message, request={}", request);

        return Uni.createFrom().voidItem();
    }
}
