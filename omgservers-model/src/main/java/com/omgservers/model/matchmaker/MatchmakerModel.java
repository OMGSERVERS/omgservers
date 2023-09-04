package com.omgservers.model.matchmaker;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerModel {

    public static void validate(MatchmakerModel matchmaker) {
        if (matchmaker == null) {
            throw new ServerSideBadRequestException("matchmaker is null");
        }
    }

    Long id;
    Instant created;
    Instant modified;
    Long tenantId;
    Long stageId;
}
