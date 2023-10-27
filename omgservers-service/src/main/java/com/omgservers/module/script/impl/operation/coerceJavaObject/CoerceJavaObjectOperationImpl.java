package com.omgservers.module.script.impl.operation.coerceJavaObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CoerceJavaObjectOperationImpl implements CoerceJavaObjectOperation {

    final ObjectMapper objectMapper;

    @Override
    public LuaValue coerceJavaObject(Object object) {
        try {
            // TODO: direct transformation from java to lua
            final var jsonString = objectMapper.writeValueAsString(object);
            final var luaValue = objectMapper.readValue(jsonString, LuaValue.class);
            return luaValue;
        } catch (IOException e) {
            throw new ServerSideBadRequestException("coerce from java to lua failed, " + e.getMessage(), e);
        }
    }
}
