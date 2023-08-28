package com.omgservers.module.context.impl.service.handlerService.impl.method.handleAddActorRuntimeCommand;

import com.omgservers.dto.handler.HandleAddActorRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleAddActorRuntimeCommandMethodImpl implements HandleAddActorRuntimeCommandMethod {

    @Override
    public Uni<Void> handleAddActorRuntimeCommand(final HandleAddActorRuntimeCommandRequest request) {
        HandleAddActorRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem();
    }
}
