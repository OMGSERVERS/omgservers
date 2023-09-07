package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchClient;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchClientMethod {
    Uni<DeleteMatchClientResponse> deleteMatchClient(DeleteMatchClientRequest request);

    default DeleteMatchClientResponse deleteMatchClient(long timeout, DeleteMatchClientRequest request) {
        return deleteMatchClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
