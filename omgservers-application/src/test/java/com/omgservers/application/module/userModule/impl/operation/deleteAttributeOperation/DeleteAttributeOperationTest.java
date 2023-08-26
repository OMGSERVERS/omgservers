package com.omgservers.application.module.userModule.impl.operation.deleteAttributeOperation;

import com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation.UpsertAttributeOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.factory.AttributeModelFactory;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.application.factory.PlayerModelFactory;
import com.omgservers.application.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteAttributeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteAttributeOperation deleteAttributeOperation;

    @Inject
    UpsertAttributeOperation upsertAttributeOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    AttributeModelFactory attributeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenAttributes_whenDeleteAttribute_thenDeleted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash());
        final var userUuid = user.getId();
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(userUuid, stageId(), PlayerConfigModel.create());
        final var playerUuid = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var attribute1 = attributeModelFactory
                .create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute1);
        final var attribute2 = attributeModelFactory
                .create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute2);

        assertTrue(deleteAttributeOperation.deleteAttribute(TIMEOUT, pgPool, shard, playerUuid, attribute2.getName()));
    }

    @Test
    void givenUnknownAttribute_whenDeleteAttribute_thenNotDeleted() {
        final var shard = 0;
        assertFalse(deleteAttributeOperation.deleteAttribute(TIMEOUT, pgPool, shard, playerId(), attributeName()));
    }

    String passwordHash() {
        return "passwordhash";
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long playerId() {
        return generateIdOperation.generateId();
    }

    String attributeName() {
        return "attribute-" + UUID.randomUUID();
    }

    String stringValue() {
        return UUID.randomUUID().toString();
    }
}