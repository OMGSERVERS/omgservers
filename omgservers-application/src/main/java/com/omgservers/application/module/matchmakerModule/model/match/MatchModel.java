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

    static public MatchModel create(final UUID matchmaker,
                                    final MatchConfigModel config) {
        return create(matchmaker, UUID.randomUUID(), config);
    }

    static public MatchModel create(final UUID matchmaker,
                                    final UUID uuid,
                                    final MatchConfigModel config) {
        Instant now = Instant.now();

        final var match = new MatchModel();
        match.setMatchmaker(matchmaker);
        match.setCreated(now);
        match.setModified(now);
        match.setUuid(uuid);
        match.setRuntime(UUID.randomUUID());
        match.setConfig(config);
        return match;
    }

    static public void validate(MatchModel match) {
        if (match == null) {
            throw new ServerSideBadRequestException("match is null");
        }
    }

    UUID matchmaker;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    UUID runtime;
    MatchConfigModel config;
}
