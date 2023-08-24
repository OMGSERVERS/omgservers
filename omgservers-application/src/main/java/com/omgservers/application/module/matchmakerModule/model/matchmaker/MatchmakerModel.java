package com.omgservers.application.module.matchmakerModule.model.matchmaker;

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
public class MatchmakerModel {

    static public void validate(MatchmakerModel matchmaker) {
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
