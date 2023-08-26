package com.omgservers.application.factory;

import com.omgservers.base.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PlayerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PlayerModel create(final Long userId,
                              final Long stageId,
                              final PlayerConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, stageId, config);
    }

    public PlayerModel create(final Long id,
                              final Long userId,
                              final Long stageId,
                              final PlayerConfigModel config) {
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }
        if (userId == null) {
            throw new ServerSideBadRequestException("userId is null");
        }
        if (stageId == null) {
            throw new ServerSideBadRequestException("stageId is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        Instant now = Instant.now();

        PlayerModel player = new PlayerModel();
        player.setId(id);
        player.setUserId(userId);
        player.setCreated(now);
        player.setModified(now);
        player.setStageId(stageId);
        player.setConfig(config);

        return player;
    }
}
