package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatchmaker;

import com.omgservers.dto.matchmaker.DeleteMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerShardResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request);

    default DeleteMatchmakerShardResponse deleteMatchmaker(long timeout, DeleteMatchmakerShardedRequest request) {
        return deleteMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
