package com.omgservers.application.module.tenantModule.model.stage;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageModel {

    static public StageModel create(final UUID project) {
        // TODO: improve it
        final var secret = String.valueOf(Math.abs(new SecureRandom().nextLong()));
        return create(project, UUID.randomUUID(), null, secret, UUID.randomUUID(), StageConfigModel.create());
    }

    static public StageModel create(final UUID project,
                                    final UUID uuid,
                                    final UUID version,
                                    final String secret,
                                    final UUID matchmaker,
                                    final StageConfigModel config) {
        if (project == null) {
            throw new ServerSideBadRequestException("project is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }
        if (secret == null) {
            throw new ServerSideBadRequestException("secret is null");
        }
        if (matchmaker == null) {
            throw new ServerSideBadRequestException("matchmaker is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        var now = Instant.now();

        StageModel stage = new StageModel();
        stage.setProject(project);
        stage.setCreated(now);
        stage.setModified(now);
        stage.setUuid(uuid);
        stage.setVersion(version);
        stage.setSecret(secret);
        stage.setMatchmaker(matchmaker);
        stage.setConfig(config);
        return stage;
    }

    UUID project;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    UUID version;
    @ToString.Exclude
    String secret;
    UUID matchmaker;
    @ToString.Exclude
    StageConfigModel config;
}
