package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerState;

import com.omgservers.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerStateMethod {

    Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(UpdateMatchmakerStateRequest request);
}
