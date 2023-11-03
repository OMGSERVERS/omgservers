package com.omgservers.model.user;

public enum UserRoleEnum {
    SUPPORT(Names.SUPPORT),
    DEVELOPER(Names.DEVELOPER),
    PLAYER(Names.PLAYER),
    CONTAINER(Names.CONTAINER);

    final String name;

    UserRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public class Names {
        static public final String SUPPORT = "support";
        static public final String DEVELOPER = "developer";
        static public final String PLAYER = "player";
        static public final String CONTAINER = "container";
    }
}
