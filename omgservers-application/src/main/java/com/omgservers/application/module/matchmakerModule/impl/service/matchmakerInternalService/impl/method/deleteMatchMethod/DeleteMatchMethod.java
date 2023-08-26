package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchMethod;

import com.omgservers.dto.matchmakerModule.DeleteMatchShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchMethod {
    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchShardRequest request);

    default DeleteMatchInternalResponse deleteMatch(long timeout, DeleteMatchShardRequest request) {
        return deleteMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
