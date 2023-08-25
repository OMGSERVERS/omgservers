package com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionStageConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoGreedyMatchmakingHelpRequest {

    static public void validate(DoGreedyMatchmakingHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    Long stageId;
    Long versionId;
    Long matchmakerId;
    List<RequestModel> requests;
    List<MatchModel> matches;
    VersionStageConfigModel stageConfig;
}
