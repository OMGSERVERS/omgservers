package com.omgservers.application.factory;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.object.ObjectModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ObjectModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ObjectModel create(final Long playerId,
                              final String name,
                              final byte[] body) {
        final var id = generateIdOperation.generateId();
        return create(id, playerId, name, body);
    }

    public ObjectModel create(final Long id,
                              final Long playerId,
                              final String name,
                              final byte[] body) {
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }
        if (playerId == null) {
            throw new ServerSideBadRequestException("player is null");
        }
        if (name == null) {
            throw new ServerSideBadRequestException("fileName is null");
        }
        if (body == null) {
            throw new ServerSideBadRequestException("body is null");
        }

        Instant now = Instant.now();

        ObjectModel object = new ObjectModel();
        object.setId(id);
        object.setPlayerId(playerId);
        object.setCreated(now);
        object.setModified(now);
        object.setName(name);
        object.setBody(body);
        return object;
    }
}
