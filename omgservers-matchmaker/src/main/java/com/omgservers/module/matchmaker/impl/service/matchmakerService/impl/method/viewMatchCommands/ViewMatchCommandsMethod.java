package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchCommands;

import com.omgservers.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchCommandsMethod {
    Uni<ViewMatchCommandsResponse> viewMatchCommands(ViewMatchCommandsRequest request);
}
