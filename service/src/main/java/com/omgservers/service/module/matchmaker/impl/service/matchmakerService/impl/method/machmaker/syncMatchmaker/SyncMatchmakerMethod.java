package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.syncMatchmaker;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);
}
