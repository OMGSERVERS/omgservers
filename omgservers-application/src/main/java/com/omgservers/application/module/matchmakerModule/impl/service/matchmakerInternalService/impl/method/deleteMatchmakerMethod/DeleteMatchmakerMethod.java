package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteMatchmakerInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request);

    default DeleteMatchmakerInternalResponse deleteMatchmaker(long timeout, DeleteMatchmakerInternalRequest request) {
        return deleteMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
