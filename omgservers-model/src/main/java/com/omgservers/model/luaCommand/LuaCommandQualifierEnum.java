package com.omgservers.model.luaCommand;

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
}
