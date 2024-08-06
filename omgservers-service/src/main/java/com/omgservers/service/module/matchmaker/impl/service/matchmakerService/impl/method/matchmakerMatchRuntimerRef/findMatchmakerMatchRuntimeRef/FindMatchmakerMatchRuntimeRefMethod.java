package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.findMatchmakerMatchRuntimeRef;

import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerMatchRuntimeRefMethod {
    Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(FindMatchmakerMatchRuntimeRefRequest request);
}
