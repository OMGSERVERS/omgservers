package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatches;

import com.omgservers.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.dto.matchmaker.ViewMatchesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchesMethod {
    Uni<ViewMatchesResponse> viewMatches(ViewMatchesRequest request);
}
