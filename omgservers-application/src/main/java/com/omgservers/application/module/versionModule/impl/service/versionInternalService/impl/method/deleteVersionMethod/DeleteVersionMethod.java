package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.deleteVersionMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.DeleteVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.DeleteVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMethod {
    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionInternalRequest request);
}
