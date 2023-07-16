package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteRequestInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteRequestInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteRequestMethod {
    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request);

    default DeleteRequestInternalResponse deleteRequest(long timeout, DeleteRequestInternalRequest request) {
        return deleteRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
