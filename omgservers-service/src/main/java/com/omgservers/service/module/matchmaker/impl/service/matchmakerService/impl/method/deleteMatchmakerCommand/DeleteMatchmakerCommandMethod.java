package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerCommand;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerCommandMethod {
    Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(DeleteMatchmakerCommandRequest request);
}
