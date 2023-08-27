package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatch;

import com.omgservers.dto.matchmaker.DeleteMatchShardResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchMethod {
    Uni<DeleteMatchShardResponse> deleteMatch(DeleteMatchShardedRequest request);

    default DeleteMatchShardResponse deleteMatch(long timeout, DeleteMatchShardedRequest request) {
        return deleteMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
