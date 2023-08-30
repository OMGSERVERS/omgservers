package com.omgservers.module.context.impl.luaEvent.runtime;

import com.omgservers.module.context.TestLuaGlobals;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class LuaHandleEventRuntimeCommandReceivedEventTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    TestLuaGlobals testLuaGlobals;

    @Inject
    CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void testLuaHandleEventRuntimeCommandReceivedEvent() {
        final var luaGlobals = testLuaGlobals.createTestGlobalsForScript("""
                -- main.lua
                   
                function runtime_handle_event(event, runtime)
                    print("event.id=" .. event.id)
                    print("event.user_id=" .. event.user_id)
                    print("event.player_id=" .. event.player_id)
                    print("event.client_id=" .. event.client_id)
                    print("event.data=" .. event.data)
                    print("runtime.matchmaker_id=" .. runtime.matchmaker_id)
                    print("runtime.match_id=" .. runtime.match_id)
                    print("runtime.runtime_id=" .. runtime.runtime_id)
                    -- assert
                    assert(event.id == "runtime_handle_event", "event.id is wrong")
                    assert(event.user_id == "27535430859688960", "event.user_id is wrong")
                    assert(event.player_id == "27535430859688961", "event.player_id is wrong")
                    assert(event.client_id == "27535430859688962", "event.client_id is wrong")
                    assert(event.data == "arbitrary", "event.data is wrong")
                    assert(runtime.matchmaker_id == "27535430859688964", "runtime.matchmaker_id is wrong")
                    assert(runtime.match_id == "27535430859688965", "runtime.match_id is wrong")
                    assert(runtime.runtime_id == "27535430859688966", "runtime.runtime_id is wrong")
                end
                """);

        final var luaEvent = new LuaHandleEventRuntimeCommandReceivedEvent(
                27535430859688960L,
                27535430859688961L,
                27535430859688962L,
                "arbitrary");
        final var luaContext = createLuaRuntimeContextOperation.createLuaRuntimeContext(TIMEOUT,
                27535430859688964L,
                27535430859688965L,
                27535430859688966L);

        luaGlobals.handleEvent(luaEvent, luaContext);
    }

}