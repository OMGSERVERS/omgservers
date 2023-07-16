package com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation;

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
class UpsertAttributeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertAttributeOperation upsertAttributeOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenPlayer_whenUpsertAttribute_thenInserted() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, passwordHash());
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        final var attribute = AttributeModel.create(playerUuid, attributeName(), stringValue());
        assertTrue(upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute));
    }

    @Test
    void givenAttribute_whenUpsertAttribute_thenUpdated() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var attribute = AttributeModel.create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute);

        assertFalse(upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute));
    }

    String passwordHash() {
        return "passwordhash";
    }

    String attributeName() {
        return "attribute-" + UUID.randomUUID();
    }

    String stringValue() {
        return UUID.randomUUID().toString();
    }
}