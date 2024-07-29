package com.omgservers.service.factory.system;

import com.omgservers.schema.model.index.IndexConfigModel;
import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class IndexModelFactory {

    final GenerateIdOperation generateIdOperation;

    public IndexModel create(final IndexConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, config, idempotencyKey);
    }

    public IndexModel create(final IndexConfigModel config,
                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, config, idempotencyKey);
    }

    public IndexModel create(final Long id,
                             final IndexConfigModel config) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, config, idempotencyKey);
    }

    public IndexModel create(final Long id,
                             final IndexConfigModel config,
                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var index = new IndexModel();
        index.setId(id);
        index.setIdempotencyKey(idempotencyKey);
        index.setCreated(now);
        index.setModified(now);
        index.setConfig(config);
        index.setDeleted(false);
        return index;
    }
}
