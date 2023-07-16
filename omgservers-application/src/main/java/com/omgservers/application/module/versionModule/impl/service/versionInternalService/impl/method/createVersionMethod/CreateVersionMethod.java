package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.createVersionMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.CreateVersionInternalRequest;
import io.smallrye.mutiny.Uni;

public interface CreateVersionMethod {
    Uni<Void> createVersion(CreateVersionInternalRequest request);
}
