package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.viewMatchmakerCommands;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerCommandsMethod {
    Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(ViewMatchmakerCommandsRequest request);
}
