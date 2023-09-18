package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl;

import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl.context.function.LuaPlayerSetAttributeFunctionFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.module.user.impl.operation.upsertUser.UpsertUserOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaString;

@Slf4j
@QuarkusTest
class LuaPlayerSetAttributeFunctionTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    LuaPlayerSetAttributeFunctionFactory setAttributeFunctionFactory;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenPlayer_whenLuaPlayerSetStringAttributeFunction_thenAttributeInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var setStringAttributeFunction = setAttributeFunctionFactory.build(user.getId(), player.getId());
        final var luaResult = setStringAttributeFunction
                .call(LuaString.valueOf("key"), LuaString.valueOf("value"));
        assertTrue(luaResult.isnil());
    }

    @Test
    void givenUnknownUuids_whenLuaPlayerSetStringAttributeFunction_thenErrorReturned() {
        final var userId = generateIdOperation.generateId();
        final var playerId = generateIdOperation.generateId();
        final var setStringAttributeFunction = setAttributeFunctionFactory.build(userId, playerId);
        final var luaResult = setStringAttributeFunction
                .call(LuaString.valueOf("key"), LuaString.valueOf("value"));
        assertNotNull(luaResult.checkjstring());
        log.info(luaResult.checkjstring());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}