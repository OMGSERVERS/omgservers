package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionRuntime;

import com.omgservers.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.dto.tenant.GetVersionRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionRuntimeMethod {

    Uni<GetVersionRuntimeResponse> getVersionRuntime(GetVersionRuntimeRequest request);
}
