package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.luaModule.impl.runtime.TestLuaRuntime;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
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
import org.luaj.vm2.LuaString;

import java.util.UUID;

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
    PgPool pgPool;

    @Test
    void givenPlayer_whenLuaPlayerSetStringAttributeFunction_thenAttributeInserted() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var setStringAttributeFunction = setAttributeFunctionFactory.build(user.getUuid(), player.getUuid());
        final var luaResult = setStringAttributeFunction
                .call(LuaString.valueOf("key"), LuaString.valueOf("value"));
        assertTrue(luaResult.isnil());
    }

    @Test
    void givenUnknownUuids_whenLuaPlayerSetStringAttributeFunction_thenErrorReturned() {
        final var user = UUID.randomUUID();
        final var player = UUID.randomUUID();
        final var setStringAttributeFunction = setAttributeFunctionFactory.build(user, player);
        final var luaResult = setStringAttributeFunction
                .call(LuaString.valueOf("key"), LuaString.valueOf("value"));
        assertNotNull(luaResult.checkjstring());
        log.info(luaResult.checkjstring());
    }
}