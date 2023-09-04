package com.omgservers.model.match;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {

    public static void validate(MatchModel match) {
        if (match == null) {
            throw new ServerSideBadRequestException("match is null");
        }
    }

    Long id;
    Long matchmakerId;
    Instant created;
    Instant modified;
    Long runtimeId;
    @EqualsAndHashCode.Exclude
    MatchConfigModel config;
}
