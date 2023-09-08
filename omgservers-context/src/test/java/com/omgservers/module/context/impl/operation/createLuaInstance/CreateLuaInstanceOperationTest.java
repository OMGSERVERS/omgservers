package com.omgservers.module.context.impl.operation.createLuaInstance;

import com.omgservers.module.context.impl.operation.createLuaGlobals.impl.LuaGlobals;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaTable;
import org.mockito.Mockito;

@Slf4j
@QuarkusTest
class CreateLuaInstanceOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    CreateLuaInstanceOperation createLuaInstanceOperation;

    @Test
    void givenLuaGlobalsAndContext_whenCreateLuaInstance_thenCreated() {
        final var luaGlobalsMock = Mockito.mock(LuaGlobals.class);
        final var luaContextMock = Mockito.mock(LuaTable.class);

        final var luaInstance = createLuaInstanceOperation.createLuaInstance(TIMEOUT, luaGlobalsMock, luaContextMock);

        assertEquals(luaGlobalsMock, luaInstance.getLuaGlobals());
        assertEquals(luaContextMock, luaInstance.getLuaContext());
    }
}