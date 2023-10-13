package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchCommand;

import com.omgservers.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.dto.matchmaker.DeleteMatchCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchCommandMethod {
    Uni<DeleteMatchCommandResponse> deleteMatchCommand(DeleteMatchCommandRequest request);
}
