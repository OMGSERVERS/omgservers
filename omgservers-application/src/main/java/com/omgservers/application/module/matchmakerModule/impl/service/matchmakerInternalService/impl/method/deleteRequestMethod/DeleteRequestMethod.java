package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod;

import com.omgservers.dto.matchmakerModule.DeleteRequestShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteRequestMethod {
    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestShardRequest request);

    default DeleteRequestInternalResponse deleteRequest(long timeout, DeleteRequestShardRequest request) {
        return deleteRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
