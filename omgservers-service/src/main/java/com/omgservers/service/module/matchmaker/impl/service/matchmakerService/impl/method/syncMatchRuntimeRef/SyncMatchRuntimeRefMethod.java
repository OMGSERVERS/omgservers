package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchRuntimeRefMethod {
    Uni<SyncMatchRuntimeRefResponse> syncMatchRuntimeRef(SyncMatchRuntimeRefRequest request);
}
