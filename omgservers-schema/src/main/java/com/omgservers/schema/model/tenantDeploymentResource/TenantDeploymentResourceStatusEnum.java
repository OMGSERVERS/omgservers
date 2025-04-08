package com.omgservers.schema.model.tenantDeploymentResource;

public enum TenantDeploymentResourceStatusEnum {

    /**
     * The deployment is being created and is not yet ready to accept clients.
     */
    PENDING,

    /**
     * The deployment has been fully created and is ready to be used by clients.
     */
    CREATED,

    /**
     * The deployment has been closed and no longer accepts new client entries.
     */
    CLOSED,

}
