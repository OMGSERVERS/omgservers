package com.omgservers.service.factory.root;

import com.omgservers.model.root.RootModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RootModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RootModel create() {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();

        return create(id, idempotencyKey);
    }

    public RootModel create(final Long id) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, idempotencyKey);
    }

    public RootModel create(final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, idempotencyKey);
    }

    public RootModel create(final Long id,
                            final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var root = new RootModel();
        root.setId(id);
        root.setIdempotencyKey(idempotencyKey);
        root.setCreated(now);
        root.setModified(now);
        root.setDeleted(Boolean.FALSE);
        return root;
    }
}
