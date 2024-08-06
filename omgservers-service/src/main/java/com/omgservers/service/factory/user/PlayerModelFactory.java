package com.omgservers.service.factory.user;

import com.omgservers.schema.model.player.PlayerAttributesModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
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
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, userId, tenantId, stageId, idempotencyKey);
    }

    public PlayerModel create(final Long userId,
                              final Long tenantId,
                              final Long stageId,
                              final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, tenantId, stageId, idempotencyKey);
    }

    public PlayerModel create(final Long id,
                              final Long userId,
                              final Long tenantId,
                              final Long stageId,
                              final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var player = new PlayerModel();
        player.setId(id);
        player.setIdempotencyKey(idempotencyKey);
        player.setUserId(userId);
        player.setCreated(now);
        player.setModified(now);
        player.setTenantId(tenantId);
        player.setStageId(stageId);
        player.setAttributes(PlayerAttributesModel.create());
        player.setProfile(new Object());
        player.setDeleted(false);

        return player;
    }
}
