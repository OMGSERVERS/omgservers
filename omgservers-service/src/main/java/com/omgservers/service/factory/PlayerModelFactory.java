package com.omgservers.service.factory;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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
                              final Long stageId) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, tenantId, stageId);
    }

    public PlayerModel create(final Long id,
                              final Long userId,
                              final Long tenantId,
                              final Long stageId) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        PlayerModel player = new PlayerModel();
        player.setId(id);
        player.setUserId(userId);
        player.setCreated(now);
        player.setModified(now);
        player.setTenantId(tenantId);
        player.setStageId(stageId);
        player.setAttributes(PlayerAttributesModel.create());
        player.setObject(new Object());
        player.setDeleted(false);

        return player;
    }
}
