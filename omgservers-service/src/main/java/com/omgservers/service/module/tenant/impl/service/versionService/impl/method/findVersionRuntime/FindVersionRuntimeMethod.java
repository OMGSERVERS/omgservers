package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionRuntime;

import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionRuntimeMethod {
    Uni<FindVersionRuntimeResponse> findVersionRuntime(FindVersionRuntimeRequest request);
}
