package com.omgservers.model.match;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {

    static public void validate(MatchModel match) {
        if (match == null) {
            throw new ServerSideBadRequestException("match is null");
        }
    }

    Long id;
    Long matchmakerId;
    Instant created;
    Instant modified;
    Long runtimeId;
    MatchConfigModel config;
}
