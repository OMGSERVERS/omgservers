package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchResourceMethod {
    Uni<GetMatchmakerMatchResourceResponse> execute(GetMatchmakerMatchResourceRequest request);
}
