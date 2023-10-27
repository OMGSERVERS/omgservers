package com.omgservers.operation.enrichLuaGlobals;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;

@Slf4j
@ApplicationScoped
class EnrichLuaGlobalsOperationImpl implements EnrichLuaGlobalsOperation {

    @Override
    public Globals enrichLuaGlobals(Globals luaGlobals) {
        luaGlobals.set("print", new LuaPrintFunction(luaGlobals));
        return luaGlobals;
    }
}
