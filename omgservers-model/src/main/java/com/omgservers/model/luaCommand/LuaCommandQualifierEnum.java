package com.omgservers.model.luaCommand;

import com.omgservers.exception.ServerSideConflictException;

public enum LuaCommandQualifierEnum {

    RESPOND("respond"),
    SET_ATTRIBUTES("set_attributes"),
    SET_OBJECT("set_attributes"),

    UNICAST("unicast"),
    MULTICAST("multicast"),
    BROADCAST("broadcast"),
    KICK("kick"),
    STOP("stop");

    final String qualifier;

    LuaCommandQualifierEnum(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getQualifier() {
        return qualifier;
    }

    public static LuaCommandQualifierEnum fromString(String qualifier) {
        for (final var q : LuaCommandQualifierEnum.values()) {
            if (q.qualifier.equals(qualifier)) {
                return q;
            }
        }
        throw new ServerSideConflictException("Unknown lua command qualifier, " +
                "qualifier=" + qualifier);
    }
}
