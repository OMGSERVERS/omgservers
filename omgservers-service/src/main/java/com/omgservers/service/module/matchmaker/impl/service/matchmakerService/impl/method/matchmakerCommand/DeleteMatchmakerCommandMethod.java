package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerCommandMethod {
    Uni<DeleteMatchmakerCommandResponse> execute(DeleteMatchmakerCommandRequest request);
}
