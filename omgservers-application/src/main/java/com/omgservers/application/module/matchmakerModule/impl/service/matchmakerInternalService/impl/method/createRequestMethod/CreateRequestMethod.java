package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createRequestMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateRequestInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.CreateRequestInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateRequestMethod {
    Uni<CreateRequestInternalResponse> createRequest(CreateRequestInternalRequest request);

    default CreateRequestInternalResponse createRequest(long timeout, CreateRequestInternalRequest request) {
        return createRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
