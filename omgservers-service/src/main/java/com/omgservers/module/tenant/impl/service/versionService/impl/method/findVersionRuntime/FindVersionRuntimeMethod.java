package com.omgservers.module.tenant.impl.service.versionService.impl.method.findVersionRuntime;

import com.omgservers.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.dto.tenant.FindVersionRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionRuntimeMethod {
    Uni<FindVersionRuntimeResponse> findVersionRuntime(FindVersionRuntimeRequest request);
}
