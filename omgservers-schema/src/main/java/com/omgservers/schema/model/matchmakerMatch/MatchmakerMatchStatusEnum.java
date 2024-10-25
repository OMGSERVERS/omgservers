package com.omgservers.schema.model.matchmakerMatch;

public enum MatchmakerMatchStatusEnum {

    /**
     * The match was only created and is not yet ready to accept client entries.
     */
    PENDING,

    /**
     * The match is currently participating in matchmaking.
     */
    OPENED,

    /**
     * The match was closed to new client entries.
     */
    CLOSED,
}
