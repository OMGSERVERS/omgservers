package com.omgservers.schema.model.poolServer;

public enum PoolServerQualifierEnum {

    /**
     * Using service host as a docker host for containers.
     */
    SERVICE_DOCKER_HOST,

    /**
     * Using remote docker host to run docker containers.
     */
    REMOTE_DOCKER_HOST,
}
