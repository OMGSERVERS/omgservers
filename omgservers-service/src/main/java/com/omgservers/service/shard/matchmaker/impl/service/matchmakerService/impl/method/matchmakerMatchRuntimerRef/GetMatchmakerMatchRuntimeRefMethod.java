package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef;

import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchRuntimeRefMethod {
    Uni<GetMatchmakerMatchRuntimeRefResponse> execute(GetMatchmakerMatchRuntimeRefRequest request);
}
