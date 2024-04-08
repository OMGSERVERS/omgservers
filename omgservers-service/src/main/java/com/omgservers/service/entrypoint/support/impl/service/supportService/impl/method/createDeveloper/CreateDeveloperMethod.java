package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDeveloper;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeveloperMethod {
    Uni<CreateDeveloperSupportResponse> createDeveloper(CreateDeveloperSupportRequest request);
}
