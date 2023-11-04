package com.omgservers.worker.module.handler.lua.operation.coerceJavaObject;

import org.luaj.vm2.LuaValue;

public interface CoerceJavaObjectOperation {
    LuaValue coerceJavaObject(Object object);
}
