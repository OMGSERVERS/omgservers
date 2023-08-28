package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatch;

import com.omgservers.dto.matchmaker.DeleteMatchShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchMethod {
    Uni<DeleteMatchShardedResponse> deleteMatch(DeleteMatchShardedRequest request);

    default DeleteMatchShardedResponse deleteMatch(long timeout, DeleteMatchShardedRequest request) {
        return deleteMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
