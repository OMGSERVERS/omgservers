package com.omgservers.schema.model.poolServer;

public enum PoolServerStatusEnum {

    /**
     * The server is fully set up and ready to run Docker containers.
     */
    CREATED,

    /**
     * The server is no longer available for running new Docker containers.
     */
    CLOSED,

}
