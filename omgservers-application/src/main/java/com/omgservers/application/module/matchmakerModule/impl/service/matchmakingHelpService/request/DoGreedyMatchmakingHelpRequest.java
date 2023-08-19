package com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
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
