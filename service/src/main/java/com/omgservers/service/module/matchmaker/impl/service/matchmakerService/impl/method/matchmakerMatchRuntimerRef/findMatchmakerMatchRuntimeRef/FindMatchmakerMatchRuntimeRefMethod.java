package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.findMatchmakerMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerMatchRuntimeRefMethod {
    Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(FindMatchmakerMatchRuntimeRefRequest request);
}
