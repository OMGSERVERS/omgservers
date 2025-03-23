package com.omgservers.schema.model.tenantLobbyResource;

public enum TenantLobbyResourceStatusEnum {

    /**
     * The lobby resource is being created and is not ready to accept clients yet.
     */
    PENDING,

    /**
     * The lobby was fully created and is ready to be used by clients.
     */
    CREATED,

}
