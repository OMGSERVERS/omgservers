package com.omgservers.application.module.runtimeModule.model.actor;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ActorModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ActorModel create(final Long runtimeId,
                             final Long userId,
                             final Long clientId,
                             final ActorConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, userId, clientId, config);
    }

    public ActorModel create(final Long id,
                             final Long runtimeId,
                             final Long userId,
                             final Long clientId,
                             final ActorConfigModel config) {
        Instant now = Instant.now();

        final var actor = new ActorModel();
        actor.setId(id);
        actor.setRuntimeId(runtimeId);
        actor.setCreated(now);
        actor.setModified(now);
        actor.setUserId(userId);
        actor.setClientId(clientId);
        actor.setConfig(config);
        actor.setStatus(ActorStatusEnum.NEW);
        return actor;
    }
}
