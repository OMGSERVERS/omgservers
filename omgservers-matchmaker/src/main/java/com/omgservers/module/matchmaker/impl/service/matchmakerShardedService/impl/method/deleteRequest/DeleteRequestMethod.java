package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteRequest;

import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteRequestMethod {
    Uni<DeleteRequestShardedResponse> deleteRequest(DeleteRequestShardedRequest request);

    default DeleteRequestShardedResponse deleteRequest(long timeout, DeleteRequestShardedRequest request) {
        return deleteRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
