package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerMatchStatus;

import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerMatchStatusMethod {

    Uni<UpdateMatchmakerMatchStatusResponse> updateMatchmakerMatchStatus(UpdateMatchmakerMatchStatusRequest request);
}
