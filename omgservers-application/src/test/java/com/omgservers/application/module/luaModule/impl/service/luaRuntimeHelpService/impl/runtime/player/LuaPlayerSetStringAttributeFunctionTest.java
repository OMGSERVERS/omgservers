package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.luaModule.impl.runtime.TestLuaRuntime;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModelFactory;
import com.omgservers.application.module.userModule.model.user.UserModelFactory;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaString;

@Slf4j
@QuarkusTest
class LuaPlayerSetStringAttributeFunctionTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    TestLuaRuntime testLuaRuntime;

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
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
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

    Long stageId() {
        return generateIdOperation.generateId();
    }
}