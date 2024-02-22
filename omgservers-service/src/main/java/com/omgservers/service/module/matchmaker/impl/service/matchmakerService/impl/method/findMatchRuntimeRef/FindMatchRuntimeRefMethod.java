package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.findMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.FindMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchRuntimeRefMethod {
    Uni<FindMatchRuntimeRefResponse> findMatchRuntimeRef(FindMatchRuntimeRefRequest request);
}
