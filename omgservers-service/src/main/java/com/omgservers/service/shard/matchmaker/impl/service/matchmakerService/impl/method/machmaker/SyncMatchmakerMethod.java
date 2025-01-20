package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMethod {
    Uni<SyncMatchmakerResponse> execute(SyncMatchmakerRequest request);
}
