package com.omgservers.module.script.impl.operation.coerceJavaObject;

import org.luaj.vm2.LuaValue;

public interface CoerceJavaObjectOperation {
    LuaValue coerceJavaObject(Object object);
}
