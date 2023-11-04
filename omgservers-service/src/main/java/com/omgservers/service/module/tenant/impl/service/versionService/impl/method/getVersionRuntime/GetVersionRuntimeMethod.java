package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionRuntime;

import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionRuntimeMethod {

    Uni<GetVersionRuntimeResponse> getVersionRuntime(GetVersionRuntimeRequest request);
}
