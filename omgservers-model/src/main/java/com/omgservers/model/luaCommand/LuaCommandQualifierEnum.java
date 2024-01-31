package com.omgservers.model.luaCommand;

public enum LuaCommandQualifierEnum {

    RESPOND("respond"),
    MULTICAST("multicast"),
    BROADCAST("broadcast"),

    SET_ATTRIBUTES("set_attributes"),
    SET_PROFILE("set_profile"),

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
