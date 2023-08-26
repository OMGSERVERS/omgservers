package com.omgservers.application.factory;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.model.runtimeCommand.RuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeCommandModel create(final Long runtimeId,
                                      final RuntimeCommandBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, body);
    }

    public RuntimeCommandModel create(final Long id,
                                      final Long runtimeId,
                                      final RuntimeCommandBodyModel body) {
        Instant now = Instant.now();

        final var runtimeCommand = new RuntimeCommandModel();
        runtimeCommand.setId(id);
        runtimeCommand.setRuntimeId(runtimeId);
        runtimeCommand.setCreated(now);
        runtimeCommand.setModified(now);
        runtimeCommand.setQualifier(body.getQualifier());
        runtimeCommand.setBody(body);
        runtimeCommand.setStatus(RuntimeCommandStatusEnum.NEW);
        return runtimeCommand;
    }
}
