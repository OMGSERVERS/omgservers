package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerCommand;

import com.omgservers.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerCommandMethod {
    Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(SyncMatchmakerCommandRequest request);
}
