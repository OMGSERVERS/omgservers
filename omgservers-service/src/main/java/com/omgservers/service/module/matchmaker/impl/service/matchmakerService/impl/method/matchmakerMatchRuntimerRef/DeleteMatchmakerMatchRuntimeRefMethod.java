package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchRuntimeRefMethod {
    Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(DeleteMatchmakerMatchRuntimeRefRequest request);
}
