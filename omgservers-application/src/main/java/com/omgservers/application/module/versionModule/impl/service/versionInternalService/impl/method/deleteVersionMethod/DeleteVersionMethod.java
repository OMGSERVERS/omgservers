package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.deleteVersionMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.DeleteVersionInternalRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMethod {
    Uni<Void> deleteVersion(DeleteVersionInternalRequest request);
}
