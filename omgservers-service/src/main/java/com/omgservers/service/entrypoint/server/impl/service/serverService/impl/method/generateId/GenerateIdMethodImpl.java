package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.generateId;

import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.schema.entrypoint.server.GenerateIdServerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GenerateIdMethodImpl implements GenerateIdMethod {

    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<GenerateIdServerResponse> getId() {
        log.debug("Generate id");
        return Uni.createFrom().item(new GenerateIdServerResponse(generateIdOperation.generateId()));
    }
}
