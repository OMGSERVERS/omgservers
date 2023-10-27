package com.omgservers.model.matchCommand;

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
@JsonDeserialize(using = MatchCommandDeserializer.class)
public class MatchCommandModel {

    public static void validate(MatchCommandModel command) {
        if (command == null) {
            throw new ServerSideBadRequestException("command is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    MatchCommandQualifierEnum qualifier;

    @NotNull
    MatchCommandBodyModel body;
}
