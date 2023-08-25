package com.omgservers.model.matchmakerCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MatchmakerCommandDeserializer.class)
public class MatchmakerCommandModel {

    static public void validate(MatchmakerCommandModel command) {
        if (command == null) {
            throw new ServerSideBadRequestException("command is null");
        }
    }

    Long id;
    Long runtimeId;
    Instant created;
    Instant modified;
    MatchmakerCommandQualifierEnum qualifier;
    MatchmakerCommandBodyModel body;
    MatchmakerCommandStatusEnum status;
}
