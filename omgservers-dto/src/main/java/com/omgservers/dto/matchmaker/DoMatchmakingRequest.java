package com.omgservers.dto.matchmaker;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionStageConfigModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoMatchmakingRequest {

    static public void validate(DoMatchmakingRequest request) {
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
