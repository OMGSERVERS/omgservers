package com.omgservers.model.matchmakerCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MatchmakerCommandDeserializer.class)
public class MatchmakerCommandModel {

    public static void validate(MatchmakerCommandModel command) {
        if (command == null) {
            throw new ServerSideBadRequestException("command is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Long runtimeId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    MatchmakerCommandQualifierEnum qualifier;

    @NotNull
    MatchmakerCommandBodyModel body;

    @NotNull
    MatchmakerCommandStatusEnum status;
}
