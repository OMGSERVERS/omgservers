package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.syncMatchmakerMatchClient;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchClientMethod {
    Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(SyncMatchmakerMatchClientRequest request);
}
