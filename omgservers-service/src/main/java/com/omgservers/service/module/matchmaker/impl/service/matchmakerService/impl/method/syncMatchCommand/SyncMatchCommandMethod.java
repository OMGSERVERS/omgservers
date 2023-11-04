package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchCommand;

import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchCommandMethod {
    Uni<SyncMatchCommandResponse> syncMatchCommand(SyncMatchCommandRequest request);
}
