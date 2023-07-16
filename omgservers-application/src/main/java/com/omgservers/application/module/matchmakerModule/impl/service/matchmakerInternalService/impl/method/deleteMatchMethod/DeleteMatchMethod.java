package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteMatchInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteMatchInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchMethod {
    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request);

    default DeleteMatchInternalResponse deleteMatch(long timeout, DeleteMatchInternalRequest request) {
        return deleteMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
