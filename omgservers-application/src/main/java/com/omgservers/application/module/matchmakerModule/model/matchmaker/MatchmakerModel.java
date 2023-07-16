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

    static public MatchmakerModel create(final UUID tenant,
                                         final UUID stage) {
        return create(UUID.randomUUID(), tenant, stage);
    }

    static public MatchmakerModel create(final UUID uuid,
                                         final UUID tenant,
                                         final UUID stage) {
        Instant now = Instant.now();

        final var matchmaker = new MatchmakerModel();
        matchmaker.setCreated(now);
        matchmaker.setUuid(uuid);
        matchmaker.setTenant(tenant);
        matchmaker.setStage(stage);
        return matchmaker;
    }

    static public void validate(MatchmakerModel matchmaker) {
        if (matchmaker == null) {
            throw new ServerSideBadRequestException("matchmaker is null");
        }
    }

    @ToString.Exclude
    Instant created;
    UUID uuid;
    UUID tenant;
    UUID stage;
}
