package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerMatchCommands;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchCommandsMethod {
    Uni<ViewMatchmakerMatchCommandsResponse> viewMatchmakerMatchCommands(ViewMatchmakerMatchCommandsRequest request);
}
