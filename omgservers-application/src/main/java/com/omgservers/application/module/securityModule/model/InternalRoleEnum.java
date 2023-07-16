package com.omgservers.application.module.securityModule.model;

public enum InternalRoleEnum {
    ADMIN(Names.ADMIN),
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
