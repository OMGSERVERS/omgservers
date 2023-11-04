package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatches;

import com.omgservers.model.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchesMethod {
    Uni<ViewMatchesResponse> viewMatches(ViewMatchesRequest request);
}
