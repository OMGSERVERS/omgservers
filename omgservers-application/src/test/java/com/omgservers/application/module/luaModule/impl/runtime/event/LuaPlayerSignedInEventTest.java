package com.omgservers.application.module.luaModule.impl.runtime.event;

import com.omgservers.application.module.luaModule.impl.runtime.TestLuaRuntime;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event.LuaPlayerSignedInEvent;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LuaPlayerSignedInEventTest extends Assertions {

    @Inject
    TestLuaRuntime testLuaRuntime;

    @Test
    void testLuaPlayerSignedInEvent() {
        final var luaRuntime = testLuaRuntime.createRuntimeForScript("""
                -- main.lua
                   
                function player_signed_in(self, event)
                    print(self)
                    print(event.id)
                end
                """);

        log.info("Finished");
    }
}