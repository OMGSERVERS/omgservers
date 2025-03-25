package com.omgservers.schema.model.deploymentLobbyResource;

public enum DeploymentLobbyResourceStatusEnum {

    /**
     * The lobby resource is being created and is not yet ready to accept clients.
     */
    PENDING,

    /**
     * The lobby resource has been fully created and is ready to be used by clients.
     */
    CREATED,

    /**
     * The lobby has been closed and no longer accepts new client requests.
     */
    CLOSED,

}
