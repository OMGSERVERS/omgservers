package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerCommandMethod {
    Uni<SyncMatchmakerCommandResponse> execute(SyncMatchmakerCommandRequest request);
}
