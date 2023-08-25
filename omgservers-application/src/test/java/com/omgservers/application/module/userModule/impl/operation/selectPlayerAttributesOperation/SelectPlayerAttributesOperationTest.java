package com.omgservers.application.module.userModule.impl.operation.selectPlayerAttributesOperation;

import com.omgservers.base.factory.AttributeModelFactory;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.base.factory.PlayerModelFactory;
import com.omgservers.base.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation.UpsertAttributeOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectPlayerAttributesOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectPlayerAttributesOperation selectAllAttributesOperation;

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
    void givenAttributes_whenSelectPlayerAttributes_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash());
        final var userUuid = user.getId();
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(userUuid, stageId(), PlayerConfigModel.create());
        final var playerUuid = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var attribute1 = attributeModelFactory.create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute1);
        final var attribute2 = attributeModelFactory.create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute2);
        final var attribute3 = attributeModelFactory.create(playerUuid, attributeName(), stringValue());
        upsertAttributeOperation.upsertAttribute(TIMEOUT, pgPool, shard, attribute3);

        final var selectedAttributes = selectAllAttributesOperation
                .selectPlayerAttributes(TIMEOUT, pgPool, shard, playerUuid);
        assertEquals(3, selectedAttributes.size());
        assertTrue(selectedAttributes.contains(attribute1));
        assertTrue(selectedAttributes.contains(attribute2));
        assertTrue(selectedAttributes.contains(attribute3));
    }

    @Test
    void givenUnknownPlayerUuid_whenSelectPlayerAttributes_thenEmptyList() {
        final var shard = 0;

        final var selectedAttributes = selectAllAttributesOperation
                .selectPlayerAttributes(TIMEOUT, pgPool, shard, playerId());
        assertTrue(selectedAttributes.isEmpty());
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