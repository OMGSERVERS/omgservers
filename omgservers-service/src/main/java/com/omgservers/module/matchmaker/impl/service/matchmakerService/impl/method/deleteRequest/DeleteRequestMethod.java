package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteRequest;

import com.omgservers.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.dto.matchmaker.DeleteRequestRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteRequestMethod {
    Uni<DeleteRequestResponse> deleteRequest(DeleteRequestRequest request);

    default DeleteRequestResponse deleteRequest(long timeout, DeleteRequestRequest request) {
        return deleteRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
