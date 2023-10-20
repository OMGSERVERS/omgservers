package com.omgservers.module.tenant.impl.service.versionService.impl.method.deleteVersionRuntime;

import com.omgservers.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.dto.tenant.DeleteVersionRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionRuntimeMethod {
    Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(DeleteVersionRuntimeRequest request);
}
