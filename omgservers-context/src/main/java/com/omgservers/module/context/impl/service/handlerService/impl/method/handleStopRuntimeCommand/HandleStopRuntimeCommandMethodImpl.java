package com.omgservers.module.context.impl.service.handlerService.impl.method.handleStopRuntimeCommand;

import com.omgservers.dto.handler.HandleStopRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleStopRuntimeCommandMethodImpl implements HandleStopRuntimeCommandMethod {

    @Override
    public Uni<Void> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request) {
        HandleStopRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem();
    }
}
