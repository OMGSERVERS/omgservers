package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerCommandMethod {
    Uni<SyncMatchmakerCommandResponse> execute(SyncMatchmakerCommandRequest request);
}
