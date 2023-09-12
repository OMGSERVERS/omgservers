package com.omgservers.module.user.impl.operation.selectAttribute;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.user.factory.AttributeModelFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.upsertAttribute.UpsertAttributeOperation;
import com.omgservers.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.module.user.impl.operation.upsertUser.UpsertUserOperation;
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
class SelectAttributeOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectAttributeOperation selectAttributeOperation;

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
    void givenAttribute_whenSelectAttribute_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash());
        final var userId = user.getId();
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(userId, tenantId(), stageId(), PlayerConfigModel.create());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var attribute1 = attributeModelFactory
                .create(userId, playerId, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute1);

        final var attribute2 = selectAttributeOperation
                .selectAttribute(TIMEOUT, pgPool, shard, userId, playerId, attribute1.getName());
        assertEquals(attribute1, attribute2);
    }

    @Test
    void givenIds_whenSelectAttribute_thenException() {
        final var shard = 0;

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectAttributeOperation
                .selectAttribute(TIMEOUT, pgPool, shard, userId(), playerId(), attributeName()));
        log.info("Exception: {}", exception.getMessage());
    }

    String passwordHash() {
        return "passwordhash";
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
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