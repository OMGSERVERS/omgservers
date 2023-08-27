package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteRequest;

import com.omgservers.dto.matchmaker.DeleteRequestShardResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteRequestMethod {
    Uni<DeleteRequestShardResponse> deleteRequest(DeleteRequestShardedRequest request);

    default DeleteRequestShardResponse deleteRequest(long timeout, DeleteRequestShardedRequest request) {
        return deleteRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
