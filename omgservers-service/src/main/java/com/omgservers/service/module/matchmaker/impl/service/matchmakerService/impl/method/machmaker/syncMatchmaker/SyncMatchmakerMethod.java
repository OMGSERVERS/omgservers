package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.syncMatchmaker;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);
}
