package com.omgservers.model.user;

public enum UserRoleEnum {
    /**
     * Role which is used to interact with the service through Admin Api.
     */
    ADMIN(Names.ADMIN),

    /**
     * Role which is used to interact with the service through Support Api.
     */
    SUPPORT(Names.SUPPORT),

    /**
     * Role which is used to interact with the service through Router Api.
     */
    ROUTER(Names.ROUTER),

    /**
     * Role which is used to interact with the service through Registry Api.
     */
    REGISTRY(Names.REGISTRY),

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
    WORKER(Names.WORKER),

    /**
     * Role which is used to connect to the service WebSocket endpoint.
     */
    WEBSOCKET(Names.WEBSOCKET);

    public static UserRoleEnum fromString(String name) {
        for (var value : UserRoleEnum.values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with name " + name);
    }

    final String name;

    UserRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public class Names {
        static public final String ADMIN = "admin";
        static public final String SUPPORT = "support";
        static public final String ROUTER = "router";
        static public final String REGISTRY = "registry";
        static public final String DEVELOPER = "developer";
        static public final String PLAYER = "player";
        static public final String WORKER = "worker";
        static public final String WEBSOCKET = "websocket";
    }
}
