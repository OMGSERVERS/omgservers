package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerRequest;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerRequestMethod {
    Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(SyncMatchmakerRequestRequest request);
}
