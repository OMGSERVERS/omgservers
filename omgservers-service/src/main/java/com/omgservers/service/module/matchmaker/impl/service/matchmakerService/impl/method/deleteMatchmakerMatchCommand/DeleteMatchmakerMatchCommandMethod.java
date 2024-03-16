package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerMatchCommand;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchCommandMethod {
    Uni<DeleteMatchCommandResponse> deleteMatchmakerMatchCommand(DeleteMatchCommandRequest request);
}
