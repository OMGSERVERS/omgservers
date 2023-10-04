package com.omgservers.module.user.factory;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.player.PlayerObjectModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PlayerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PlayerModel create(final Long userId,
                              final Long tenantId,
                              final Long stageId,
                              final PlayerConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, tenantId, stageId, config);
    }

    public PlayerModel create(final Long id,
                              final Long userId,
                              final Long tenantId,
                              final Long stageId,
                              final PlayerConfigModel config) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        PlayerModel player = new PlayerModel();
        player.setId(id);
        player.setUserId(userId);
        player.setCreated(now);
        player.setModified(now);
        player.setTenantId(tenantId);
        player.setStageId(stageId);
        player.setAttributes(PlayerAttributesModel.create());
        player.setObject(PlayerObjectModel.create());
        player.setConfig(config);

        return player;
    }
}
