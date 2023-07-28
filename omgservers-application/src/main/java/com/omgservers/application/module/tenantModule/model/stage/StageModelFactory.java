package com.omgservers.application.module.tenantModule.model.stage;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
                             final Long matchmakerId,
                             final StageConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var secret = String.valueOf(new SecureRandom().nextLong());
        return create(id, projectId, secret, matchmakerId, config);
    }

    public StageModel create(final Long id,
                             final Long projectId,
                             final String secret,
                             final Long matchmakerId,
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
        if (matchmakerId == null) {
            throw new ServerSideBadRequestException("matchmakerId is null");
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
        stage.setMatchmakerId(matchmakerId);
        stage.setConfig(config);
        return stage;
    }

}