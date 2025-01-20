package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef;

import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerMatchRuntimeRefMethod {
    Uni<FindMatchmakerMatchRuntimeRefResponse> execute(FindMatchmakerMatchRuntimeRefRequest request);
}
