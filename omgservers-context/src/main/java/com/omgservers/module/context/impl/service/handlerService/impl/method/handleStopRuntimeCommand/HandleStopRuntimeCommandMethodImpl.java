package com.omgservers.module.context.impl.service.handlerService.impl.method.handleStopRuntimeCommand;

import com.omgservers.dto.context.HandleStopRuntimeCommandRequest;
import com.omgservers.dto.context.HandleStopRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleStopRuntimeCommandMethodImpl implements HandleStopRuntimeCommandMethod {

    @Override
    public Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request) {
        HandleStopRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem()
                .replaceWith(new HandleStopRuntimeCommandResponse(true));
    }
}
