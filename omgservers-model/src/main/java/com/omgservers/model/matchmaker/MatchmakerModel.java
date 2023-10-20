package com.omgservers.model.matchmaker;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;
}
