package com.omgservers.model.user;

public enum UserRoleEnum {
    /**
     * Role which is used to interact with the service through Developer Api.
     */
    DEVELOPER(Names.DEVELOPER),

    /**
     * Role which is used to interact with the service through Player Api.
     */
    PLAYER(Names.PLAYER),

    /**
     * Role which is used to interact with the service through Worker Api.
     */
    WORKER(Names.WORKER);

    final String name;

    UserRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public class Names {
        static public final String DEVELOPER = "developer";
        static public final String PLAYER = "player";
        static public final String WORKER = "worker";
    }
}
