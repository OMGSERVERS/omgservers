package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerMatchCommand;

import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchCommandMethod {
    Uni<SyncMatchCommandResponse> syncMatchmakerMatchCommand(SyncMatchCommandRequest request);
}
