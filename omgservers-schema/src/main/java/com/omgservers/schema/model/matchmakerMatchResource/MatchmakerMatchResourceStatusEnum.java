package com.omgservers.schema.model.matchmakerMatchResource;

public enum MatchmakerMatchResourceStatusEnum {

    /**
     * The match resource is being created and is not yet ready to accept clients.
     */
    PENDING,

    /**
     * The match resource has been fully created and is ready to be used by clients.
     */
    CREATED,

    /**
     * The match has been closed and no longer accepts new client.
     */
    CLOSED,
}
