package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerMatches;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchesMethod {
    Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(ViewMatchmakerMatchesRequest request);
}
