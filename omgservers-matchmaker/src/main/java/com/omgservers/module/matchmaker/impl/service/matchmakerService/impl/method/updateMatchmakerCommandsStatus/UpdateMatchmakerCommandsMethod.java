package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerCommandsStatus;

import com.omgservers.dto.matchmaker.UpdateMatchmakerCommandsStatusRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerCommandsStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerCommandsMethod {
    Uni<UpdateMatchmakerCommandsStatusResponse> updateMatchmakerCommandsStatus(
            UpdateMatchmakerCommandsStatusRequest request);
}
