package com.omgservers.application.module.userModule.impl.operation.selectAttributeOperation;

import com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation.UpsertAttributeOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectAttributeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectAttributeOperation selectAttributeOperation;

    @Inject
    UpsertAttributeOperation upsertAttributeOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenAttribute_whenSelectAttribute_thenSelected() {
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

        final var attribute2 = selectAttributeOperation
                .selectAttribute(TIMEOUT, pgPool, shard, playerUuid, attribute1.getName());
        assertEquals(attribute1, attribute2);
    }

    @Test
    void givenUnknownPlayerUuid_whenSelectAttribute_thenServerSideNotFoundException() {
        final var shard = 0;

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectAttributeOperation
                .selectAttribute(TIMEOUT, pgPool, shard, playerUuid(), attributeName()));
        log.info("Exception: {}", exception.getMessage());
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