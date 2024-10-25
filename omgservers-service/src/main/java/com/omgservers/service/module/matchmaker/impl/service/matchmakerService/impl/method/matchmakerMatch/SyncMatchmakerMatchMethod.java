package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchMethod {
    Uni<SyncMatchmakerMatchResponse> execute(SyncMatchmakerMatchRequest request);
}
