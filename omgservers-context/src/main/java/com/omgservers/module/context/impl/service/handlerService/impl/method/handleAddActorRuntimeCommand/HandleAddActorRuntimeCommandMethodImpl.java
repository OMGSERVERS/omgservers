package com.omgservers.module.context.impl.service.handlerService.impl.method.handleAddActorRuntimeCommand;

import com.omgservers.dto.context.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddActorRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleAddActorRuntimeCommandMethodImpl implements HandleAddActorRuntimeCommandMethod {

    @Override
    public Uni<HandleAddActorRuntimeCommandResponse> handleAddActorRuntimeCommand(final HandleAddActorRuntimeCommandRequest request) {
        HandleAddActorRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem()
                .replaceWith(new HandleAddActorRuntimeCommandResponse(true));
    }
}
