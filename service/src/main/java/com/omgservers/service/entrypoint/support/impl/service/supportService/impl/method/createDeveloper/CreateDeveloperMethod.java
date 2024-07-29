package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDeveloper;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeveloperMethod {
    Uni<CreateDeveloperSupportResponse> createDeveloper(CreateDeveloperSupportRequest request);
}
