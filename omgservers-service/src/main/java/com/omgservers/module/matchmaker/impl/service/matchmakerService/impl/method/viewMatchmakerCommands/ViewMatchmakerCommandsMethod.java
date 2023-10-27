package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerCommands;

import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerCommandsMethod {
    Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(ViewMatchmakerCommandsRequest request);
}
