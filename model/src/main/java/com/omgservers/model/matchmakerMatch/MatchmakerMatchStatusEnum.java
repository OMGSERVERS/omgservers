package com.omgservers.model.matchmakerMatch;

public enum MatchmakerMatchStatusEnum {
    /**
     * Match was only created and doesn't ready to accept clients.
     */
    CREATED,

    /**
     * Match is ready to participate in matchmaking.
     */
    PREPARED,

    /**
     * Match was excluded from matchmaking.
     */
    EXCLUDED,
}
