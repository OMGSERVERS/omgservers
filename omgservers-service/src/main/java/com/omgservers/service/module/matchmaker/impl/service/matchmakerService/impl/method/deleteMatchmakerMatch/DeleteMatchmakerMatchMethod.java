package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerMatch;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchMethod {
    Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(DeleteMatchmakerMatchRequest request);
}
