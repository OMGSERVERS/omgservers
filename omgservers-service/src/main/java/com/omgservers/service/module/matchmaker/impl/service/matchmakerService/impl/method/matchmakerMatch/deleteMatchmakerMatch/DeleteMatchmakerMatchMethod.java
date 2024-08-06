package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.deleteMatchmakerMatch;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchMethod {
    Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(DeleteMatchmakerMatchRequest request);
}
