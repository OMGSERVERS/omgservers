package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatchClient;

import com.omgservers.dto.matchmaker.DeleteMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchClientMethod {
    Uni<DeleteMatchClientShardedResponse> deleteMatchClient(DeleteMatchClientShardedRequest request);

    default DeleteMatchClientShardedResponse deleteMatchClient(long timeout, DeleteMatchClientShardedRequest request) {
        return deleteMatchClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
