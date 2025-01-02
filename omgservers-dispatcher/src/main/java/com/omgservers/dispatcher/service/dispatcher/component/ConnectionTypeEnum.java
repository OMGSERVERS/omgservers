package com.omgservers.dispatcher.service.dispatcher.component;

public enum ConnectionTypeEnum {
    /**
     * The connection was routed to a different server.
     */
    ROUTED,

    /**
     * The connection is managed by this server.
     */
    SERVER,
}
