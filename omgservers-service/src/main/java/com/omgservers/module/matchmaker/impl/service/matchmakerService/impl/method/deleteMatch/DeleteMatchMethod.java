package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatch;

import com.omgservers.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.dto.matchmaker.DeleteMatchRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchMethod {
    Uni<DeleteMatchResponse> deleteMatch(DeleteMatchRequest request);

    default DeleteMatchResponse deleteMatch(long timeout, DeleteMatchRequest request) {
        return deleteMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
