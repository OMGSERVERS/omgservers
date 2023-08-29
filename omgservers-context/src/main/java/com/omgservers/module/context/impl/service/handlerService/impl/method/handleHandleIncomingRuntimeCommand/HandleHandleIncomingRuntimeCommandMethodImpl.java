package com.omgservers.module.context.impl.service.handlerService.impl.method.handleHandleIncomingRuntimeCommand;

import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleHandleIncomingRuntimeCommandMethodImpl implements HandleHandleIncomingRuntimeCommandMethod {

    @Override
    public Uni<HandleHandleIncomingRuntimeCommandResponse> handleHandleIncomingRuntimeCommand(HandleHandleIncomingRuntimeCommandRequest request) {
        HandleHandleIncomingRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem()
                .replaceWith(new HandleHandleIncomingRuntimeCommandResponse(true));
    }
}
