package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.GetMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchRuntimeRefMethod {
    Uni<GetMatchRuntimeRefResponse> getMatchRuntimeRef(GetMatchRuntimeRefRequest request);
}
