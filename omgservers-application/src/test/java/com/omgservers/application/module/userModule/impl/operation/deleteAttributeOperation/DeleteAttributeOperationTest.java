package com.omgservers.application.module.userModule.impl.operation.deleteAttributeOperation;

import com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation.UpsertAttributeOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
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
    PgPool pgPool;

    @Test
    void givenAttributes_whenDeleteAttribute_thenDeleted() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, passwordHash());
        final var userUuid = user.getUuid();
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(userUuid, stageUuid(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var attribute1 = AttributeModel
                .create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute1);
        final var attribute2 = AttributeModel
                .create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute2);

        assertTrue(deleteAttributeOperation.deleteAttribute(TIMEOUT, pgPool, shard, playerUuid, attribute2.getName()));
    }

    @Test
    void givenUnknownAttribute_whenDeleteAttribute_thenNotDeleted() {
        final var shard = 0;
        assertFalse(deleteAttributeOperation.deleteAttribute(TIMEOUT, pgPool, shard, playerUuid(), attributeName()));
    }

    String passwordHash() {
        return "passwordhash";
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }

    UUID playerUuid() {
        return UUID.randomUUID();
    }

    String attributeName() {
        return "attribute-" + UUID.randomUUID();
    }

    String stringValue() {
        return UUID.randomUUID().toString();
    }
}