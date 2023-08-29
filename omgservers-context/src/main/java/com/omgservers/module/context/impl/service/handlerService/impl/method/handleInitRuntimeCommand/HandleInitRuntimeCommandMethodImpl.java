package com.omgservers.module.context.impl.service.handlerService.impl.method.handleInitRuntimeCommand;

import com.omgservers.dto.context.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleInitRuntimeCommandMethodImpl implements HandleInitRuntimeCommandMethod {

    @Override
    public Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request) {
        HandleInitRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem()
                .replaceWith(new HandleInitRuntimeCommandResponse(true));
    }
}
