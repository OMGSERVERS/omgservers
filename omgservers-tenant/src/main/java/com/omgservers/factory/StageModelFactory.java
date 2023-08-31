package com.omgservers.factory;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.stage.StageModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public StageModel create(final Long projectId,
                             final StageConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var secret = String.valueOf(new SecureRandom().nextLong());
        return create(id, projectId, secret, config);
    }

    public StageModel create(final Long id,
                             final Long projectId,
                             final String secret,
                             final StageConfigModel config) {
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }
        if (projectId == null) {
            throw new ServerSideBadRequestException("projectId is null");
        }
        if (secret == null) {
            throw new ServerSideBadRequestException("secret is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        var now = Instant.now();

        StageModel stage = new StageModel();
        stage.setId(id);
        stage.setProjectId(projectId);
        stage.setCreated(now);
        stage.setModified(now);
        stage.setSecret(secret);
        stage.setConfig(config);
        return stage;
    }

}
