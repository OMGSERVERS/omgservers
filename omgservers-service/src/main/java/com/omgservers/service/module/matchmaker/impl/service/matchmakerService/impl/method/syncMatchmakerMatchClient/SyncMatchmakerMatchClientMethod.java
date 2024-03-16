package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerMatchClient;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchClientMethod {
    Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(SyncMatchmakerMatchClientRequest request);
}
