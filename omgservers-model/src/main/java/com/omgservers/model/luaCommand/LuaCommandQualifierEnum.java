package com.omgservers.model.luaCommand;

public enum LuaCommandQualifierEnum {

    RESPOND_CLIENT("respond_client"),
    MULTICAST_MESSAGE("multicast_message"),
    BROADCAST_MESSAGE("broadcast_message"),

    SET_ATTRIBUTES("set_attributes"),
    SET_PROFILE("set_profile"),

    KICK_CLIENT("kick_client"),
    STOP_MATCHMAKING("stop_matchmaking");

    final String qualifier;

    LuaCommandQualifierEnum(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getQualifier() {
        return qualifier;
    }
}
