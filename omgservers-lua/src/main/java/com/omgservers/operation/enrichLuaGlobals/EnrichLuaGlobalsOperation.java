package com.omgservers.operation.enrichLuaGlobals;

import org.luaj.vm2.Globals;

public interface EnrichLuaGlobalsOperation {

    Globals enrichLuaGlobals(Globals luaGlobals);
}
