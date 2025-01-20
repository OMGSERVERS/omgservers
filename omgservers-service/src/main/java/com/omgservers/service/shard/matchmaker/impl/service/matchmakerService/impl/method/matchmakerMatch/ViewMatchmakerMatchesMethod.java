package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchesMethod {
    Uni<ViewMatchmakerMatchesResponse> execute(ViewMatchmakerMatchesRequest request);
}
