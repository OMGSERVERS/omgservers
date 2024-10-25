package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.module.matchmaker.UpdateMatchmakerMatchStatusRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerMatchStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerMatchStatusMethod {
    Uni<UpdateMatchmakerMatchStatusResponse> execute(UpdateMatchmakerMatchStatusRequest request);
}
