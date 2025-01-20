package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchMethod {
    Uni<GetMatchmakerMatchResponse> execute(GetMatchmakerMatchRequest request);
}
