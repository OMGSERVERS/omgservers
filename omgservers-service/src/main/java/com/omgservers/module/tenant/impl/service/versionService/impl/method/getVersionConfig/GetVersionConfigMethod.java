package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionConfig;

import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionConfigMethod {

    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);
}
