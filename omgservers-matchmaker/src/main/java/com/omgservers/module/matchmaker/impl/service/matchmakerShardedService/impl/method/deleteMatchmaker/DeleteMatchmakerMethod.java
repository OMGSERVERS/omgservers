package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatchmaker;

import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerShardedResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request);

    default DeleteMatchmakerShardedResponse deleteMatchmaker(long timeout, DeleteMatchmakerShardedRequest request) {
        return deleteMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
