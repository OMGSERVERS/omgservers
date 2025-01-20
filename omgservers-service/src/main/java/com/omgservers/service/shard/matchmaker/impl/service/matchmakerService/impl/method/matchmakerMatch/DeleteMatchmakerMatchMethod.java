package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchMethod {
    Uni<DeleteMatchmakerMatchResponse> execute(DeleteMatchmakerMatchRequest request);
}
