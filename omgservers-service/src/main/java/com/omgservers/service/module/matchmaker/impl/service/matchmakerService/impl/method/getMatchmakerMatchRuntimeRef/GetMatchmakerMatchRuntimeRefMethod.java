package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchRuntimeRefMethod {
    Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(GetMatchmakerMatchRuntimeRefRequest request);
}
