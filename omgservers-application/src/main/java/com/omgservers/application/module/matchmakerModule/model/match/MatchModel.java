package com.omgservers.application.module.matchmakerModule.model.match;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

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
