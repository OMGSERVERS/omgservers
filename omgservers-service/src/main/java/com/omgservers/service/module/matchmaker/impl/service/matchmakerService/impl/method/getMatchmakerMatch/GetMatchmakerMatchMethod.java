package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerMatch;

import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchMethod {
    Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(GetMatchmakerMatchRequest request);
}
