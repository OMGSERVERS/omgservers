package com.omgservers.schema.model.internalRole;

public enum InternalRoleEnum {

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
        static public final String SERVICE = "service";
    }
}
