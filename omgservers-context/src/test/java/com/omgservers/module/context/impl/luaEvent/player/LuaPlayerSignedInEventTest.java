package com.omgservers.module.context.impl.luaEvent.player;

import com.omgservers.module.context.TestLuaGlobals;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LuaPlayerSignedInEventTest extends Assertions {

    @Inject
    TestLuaGlobals testLuaGlobals;

    @Test
    void testLuaPlayerSignedInEvent() {
        final var luaGlobals = testLuaGlobals.createTestGlobalsForScript("""
                -- main.lua
                   
                function player_signed_in(self, event)
                    print("fromlua")
                    print(self)
                    print(event.id)
                end
                """);

        log.info("Finished");
    }
}