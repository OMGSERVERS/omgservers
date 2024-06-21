package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.syncMatchmakerMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchRuntimeRefMethod {
    Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            SyncMatchmakerMatchRuntimeRefRequest request);
}
