package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerRequestMethod {
    Uni<SyncMatchmakerRequestResponse> execute(SyncMatchmakerRequestRequest request);
}
