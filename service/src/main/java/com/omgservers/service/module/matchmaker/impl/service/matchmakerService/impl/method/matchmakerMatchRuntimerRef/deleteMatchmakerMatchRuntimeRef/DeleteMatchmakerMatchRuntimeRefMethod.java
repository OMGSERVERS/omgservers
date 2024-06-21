package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.deleteMatchmakerMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchRuntimeRefMethod {
    Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(
            DeleteMatchmakerMatchRuntimeRefRequest request);
}
