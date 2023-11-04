package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerState;

import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerStateMethod {

    Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(UpdateMatchmakerStateRequest request);
}
