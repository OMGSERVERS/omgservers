package com.omgservers.application.module.matchmakerModule.model.match;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.versionModule.model.VersionModeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchConfigModel {

    static public MatchConfigModel create(final UUID tenant,
                                          final UUID stage,
                                          final UUID version,
                                          final VersionModeModel config) {
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }
        if (stage == null) {
            throw new ServerSideBadRequestException("stage is null");
        }
        if (version == null) {
            throw new ServerSideBadRequestException("version is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        final var matchConfig = new MatchConfigModel();
        matchConfig.setTenant(tenant);
        matchConfig.setStage(stage);
        matchConfig.setVersion(version);
        matchConfig.setModeConfig(config);
        matchConfig.setGroups(new ArrayList<>());
        config.getGroups().forEach(groupConfig -> {
            final var group = MatchGroupModel.create(groupConfig);
            matchConfig.getGroups().add(group);
        });
        return matchConfig;
    }

    static public void validateMatchmakerRequestConfigModel(MatchConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    UUID tenant;
    UUID stage;
    UUID version;
    VersionModeModel modeConfig;
    List<MatchGroupModel> groups;
}
