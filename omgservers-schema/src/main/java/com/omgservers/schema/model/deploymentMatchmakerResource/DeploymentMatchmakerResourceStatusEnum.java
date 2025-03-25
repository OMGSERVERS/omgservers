package com.omgservers.schema.model.deploymentMatchmakerResource;

public enum DeploymentMatchmakerResourceStatusEnum {

    /**
     * The matchmaker resource is being created and is not ready yet.
     */
    PENDING,

    /**
     * The matchmaker was fully created and is ready to handle client requests.
     */
    CREATED,

    /**
     * The matchmaker has been closed and no longer accepts new client requests.
     */
    CLOSED,
}
