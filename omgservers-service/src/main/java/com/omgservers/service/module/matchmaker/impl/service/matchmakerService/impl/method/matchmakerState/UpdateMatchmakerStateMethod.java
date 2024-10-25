package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerStateMethod {

    Uni<UpdateMatchmakerStateResponse> execute(UpdateMatchmakerStateRequest request);
}
