package com.omgservers.module.context.impl.service.handlerService.impl.method.handleInitRuntimeCommand;

import com.omgservers.dto.handler.HandleInitRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleInitRuntimeCommandMethodImpl implements HandleInitRuntimeCommandMethod {

    @Override
    public Uni<Void> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request) {
        HandleInitRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem();
    }
}
