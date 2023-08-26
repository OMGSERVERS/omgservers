package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod;

import com.omgservers.dto.matchmakerModule.DeleteMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerShardRequest request);

    default DeleteMatchmakerInternalResponse deleteMatchmaker(long timeout, DeleteMatchmakerShardRequest request) {
        return deleteMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
