package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionRuntime;

import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionRuntimeMethod {
    Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(DeleteVersionRuntimeRequest request);
}
