package com.omgservers.operation.enrichLuaGlobals;

import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

@Slf4j
class LuaPrintFunction extends VarArgFunction {

    final LuaValue tostring;

    LuaPrintFunction(Globals luaGlobals) {
        tostring = luaGlobals.get("tostring");
    }

    public Varargs invoke(Varargs args) {
        StringBuilder result = new StringBuilder();
        for (int i = 1, n = args.narg(); i <= n; i++) {
            if (i > 1) {
                result.append(' ');
            }
            LuaString s = tostring.call(args.arg(i)).strvalue();
            result.append(s.tojstring());
        }
        log.info("Lua, log={}", result);
        return NONE;
    }
}
