package com.omgservers.module.context.impl.service.handlerService.impl.method.handleDeleteActorRuntimeCommand;

import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleDeleteActorRuntimeCommandMethodImpl implements HandleDeleteActorRuntimeCommandMethod {

    @Override
    public Uni<HandleDeleteActorRuntimeCommandResponse> handleDeleteActorRuntimeCommand(HandleDeleteActorRuntimeCommandRequest request) {
        HandleDeleteActorRuntimeCommandRequest.validate(request);
        // TODO: implement
        return Uni.createFrom().voidItem()
                .replaceWith(new HandleDeleteActorRuntimeCommandResponse(true));
    }
}
