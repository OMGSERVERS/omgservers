package com.omgservers.application.module.runtimeModule.model.command;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class CommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public CommandModel create(final Long runtimeId,
                               final CommandBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, body);
    }

    public CommandModel create(final Long id,
                               final Long runtimeId,
                               final CommandBodyModel body) {
        Instant now = Instant.now();

        final var command = new CommandModel();
        command.setId(id);
        command.setRuntimeId(runtimeId);
        command.setCreated(now);
        command.setModified(now);
        command.setQualifier(body.getQualifier());
        command.setBody(body);
        command.setStatus(CommandStatusEnum.NEW);
        return command;
    }
}
