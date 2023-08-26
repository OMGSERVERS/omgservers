package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod;

import com.omgservers.dto.matchmakerModule.DeleteRequestRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteRequestMethod {
    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestRoutedRequest request);

    default DeleteRequestInternalResponse deleteRequest(long timeout, DeleteRequestRoutedRequest request) {
        return deleteRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
