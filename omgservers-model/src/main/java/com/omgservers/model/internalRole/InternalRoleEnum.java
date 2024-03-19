package com.omgservers.model.internalRole;

public enum InternalRoleEnum {

    /**
     * Internal admin role which is used to change server configuration through Admin Api.
     */
    ADMIN(Names.ADMIN),

    /**
     * Internal service role which is used to do interserver requests.
     */
    SERVICE(Names.SERVICE);

    final String name;

    InternalRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public class Names {
        static public final String ADMIN = "admin";
        static public final String SERVICE = "service";
    }
}
