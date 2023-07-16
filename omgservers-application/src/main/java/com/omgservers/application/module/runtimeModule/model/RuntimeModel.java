package com.omgservers.application.module.runtimeModule.model;

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
public class RuntimeModel {

    static public RuntimeModel create(final UUID matchmaker,
                                      final UUID match,
                                      final RuntimeConfigModel config) {
        return create(UUID.randomUUID(), matchmaker, match, config);
    }

    static public RuntimeModel create(final UUID uuid,
                                      final UUID matchmaker,
                                      final UUID match,
                                      final RuntimeConfigModel config) {
        Instant now = Instant.now();

        final var runtime = new RuntimeModel();
        runtime.setCreated(now);
        runtime.setUuid(uuid);
        runtime.setMatchmaker(matchmaker);
        runtime.setMatch(match);
        runtime.setConfig(config);
        return runtime;
    }

    static public void validateRuntimeModel(RuntimeModel runtime) {
        if (runtime == null) {
            throw new ServerSideBadRequestException("runtime is null");
        }
    }

    @ToString.Exclude
    Instant created;
    UUID uuid;
    UUID matchmaker;
    UUID match;
    RuntimeConfigModel config;
}
