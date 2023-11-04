package com.omgservers.service.factory;

import com.omgservers.model.object.ObjectModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ObjectModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ObjectModel create(final Long userId,
                              final Long playerId,
                              final String name,
                              final byte[] body) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, playerId, name, body);
    }

    public ObjectModel create(final Long id,
                              final Long userId,
                              final Long playerId,
                              final String name,
                              final byte[] body) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        ObjectModel object = new ObjectModel();
        object.setId(id);
        object.setUserId(userId);
        object.setPlayerId(playerId);
        object.setCreated(now);
        object.setModified(now);
        object.setName(name);
        object.setBody(body);
        return object;
    }
}
