package com.omgservers.model.match;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.version.VersionModeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchConfigModel {

    static public MatchConfigModel create(final Long tenantId,
                                          final Long stageId,
                                          final Long versionId,
                                          final VersionModeModel config) {
        if (tenantId == null) {
            throw new ServerSideBadRequestException("tenantId is null");
        }
        if (stageId == null) {
            throw new ServerSideBadRequestException("stageId is null");
        }
        if (versionId == null) {
            throw new ServerSideBadRequestException("version is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        final var matchConfig = new MatchConfigModel();
        matchConfig.setTenantId(tenantId);
        matchConfig.setStageId(stageId);
        matchConfig.setVersionId(versionId);
        matchConfig.setModeConfig(config);
        matchConfig.setGroups(new ArrayList<>());
        config.getGroups().forEach(groupConfig -> {
            final var group = MatchGroupModel.create(groupConfig);
            matchConfig.getGroups().add(group);
        });
        return matchConfig;
    }

    public static void validate(MatchConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    Long tenantId;
    Long stageId;
    Long versionId;
    VersionModeModel modeConfig;
    List<MatchGroupModel> groups;
}
