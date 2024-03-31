package com.omgservers.service.factory.system;

import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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

    public IndexModel create(final String name,
                             final IndexConfigModel config) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var index = new IndexModel();
        index.setId(generateIdOperation.generateId());
        index.setCreated(now);
        index.setModified(now);
        index.setName(name);
        index.setConfig(config);
        index.setDeleted(false);
        return index;
    }
}
