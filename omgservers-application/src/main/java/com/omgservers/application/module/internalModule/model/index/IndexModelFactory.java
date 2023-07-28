package com.omgservers.application.module.internalModule.model.index;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class IndexModelFactory {

    final GenerateIdOperation generateIdOperation;

    public IndexModel create(final String name,
                             final IndexConfigModel config) {
        Instant now = Instant.now();

        IndexModel model = new IndexModel();
        model.setId(generateIdOperation.generateId());
        model.setCreated(now);
        model.setModified(now);
        model.setName(name);
        model.setVersion(1L);
        model.setConfig(config);
        return model;
    }
}