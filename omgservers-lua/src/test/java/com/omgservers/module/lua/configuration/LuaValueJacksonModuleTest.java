package com.omgservers.module.lua.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.createServerGlobals.CreateServerGlobalsOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@QuarkusTest
class LuaValueJacksonModuleTest extends Assertions {

    @Inject
    CreateServerGlobalsOperation createServerGlobalsOperation;

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenLuaValue_whenWriteValueAsString_thenJson() throws IOException {
        final var globals = createServerGlobalsOperation.createServerGlobals();
        globals.finder = filename -> new ByteArrayInputStream("""
                return {
                    logical = {
                        boolean1 = true,
                        boolean2 = false
                    },
                    numbers = { 1024, 3.1415, { -128, -3.14 } },
                    string = "value",
                }
                """.getBytes(StandardCharsets.UTF_8));
        final var luaValue = globals.get("dofile").call(LuaValue.valueOf("main.lua"));

        String valueAsString = objectMapper.writeValueAsString(luaValue);
        log.info("Json, {}", valueAsString);

        final var root = objectMapper.readTree(valueAsString);
        final var logical = root.get("logical");
        assertEquals(true, logical.get("boolean1").booleanValue());
        assertEquals(false, logical.get("boolean2").booleanValue());
        final var numbers = root.get("numbers");
        assertEquals(1024, numbers.get(0).longValue());
        assertEquals(3.1415, numbers.get(1).doubleValue());
        final var subArray = numbers.get(2);
        assertEquals(-128, subArray.get(0).longValue());
        assertEquals(-3.14, subArray.get(1).doubleValue());
    }

    @Test
    void givenJson_whenReadValue_thenLuaValue() throws IOException {
        String jsonString = """
                {
                    "logical": {
                        "boolean1": true,
                        "boolean2": false
                    },
                    "numbers": [
                        1024,
                        3.1415,
                        [
                            -128,
                            -3.14
                        ]
                    ],
                    "string": "value"
                }
                """;
        final var luaValue = objectMapper.readValue(jsonString, LuaValue.class);
        assertEquals(true, luaValue.get("logical").get("boolean1").toboolean());
        assertEquals(false, luaValue.get("logical").get("boolean2").toboolean());
        assertEquals(1024, luaValue.get("numbers").get(1).tolong());
        assertEquals(3.1415, luaValue.get("numbers").get(2).todouble());
        assertEquals(-128, luaValue.get("numbers").get(3).get(1).tolong());
        assertEquals(-3.14, luaValue.get("numbers").get(3).get(2).todouble());
        assertEquals("value", luaValue.get("string").tojstring());
    }
}