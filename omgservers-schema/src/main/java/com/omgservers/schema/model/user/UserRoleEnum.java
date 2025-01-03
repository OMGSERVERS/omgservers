package com.omgservers.schema.model.user;

import lombok.Getter;

@Getter
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
     * Role which is used to interact with the service through Registry Api.
     */
    REGISTRY(Names.REGISTRY),

    /**
     * Role which is used by builder to get registry access.
     */
    BUILDER(Names.BUILDER),

    /**
     * Role which is used to do inter server requests.
     */
    SERVICE(Names.SERVICE),

    /**
     * Role which is used to do inter dispatcher requests.
     */
    DISPATCHER(Names.DISPATCHER),

    /**
     * Role which is used to interact with the service through Developer Api.
     */
    DEVELOPER(Names.DEVELOPER),

    /**
     * Role which is used to interact with the service through Player Api.
     */
    PLAYER(Names.PLAYER),

    /**
     * Role which is used to interact with the service through Runtime Api.
     */
    RUNTIME(Names.RUNTIME),

    /**
     * Role which is used to connect to the service WebSocket endpoint.
     */
    WEBSOCKET(Names.WEBSOCKET);

    public static UserRoleEnum fromString(final String name) {
        for (var value : UserRoleEnum.values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with value " + name);
    }

    final String name;

    UserRoleEnum(final String name) {
        this.name = name;
    }

    public class Names {
        static public final String ADMIN = "admin";
        static public final String SUPPORT = "support";
        static public final String REGISTRY = "registry";
        static public final String BUILDER = "builder";
        static public final String SERVICE = "service";
        static public final String DISPATCHER = "dispatcher";
        static public final String DEVELOPER = "developer";
        static public final String PLAYER = "player";
        static public final String RUNTIME = "runtime";
        static public final String WEBSOCKET = "websocket";
    }
}
