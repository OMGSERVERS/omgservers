package com.omgservers.model.matchClient;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchClientModel {

    static public void validate(MatchClientModel matchClient) {
        if (matchClient == null) {
            throw new ServerSideBadRequestException("matchClient is null");
        }
    }

    Long id;
    Long matchmakerId;
    Long matchId;
    Instant created;
    Instant modified;
    Long userId;
    Long clientId;
    Long requestId;
}
