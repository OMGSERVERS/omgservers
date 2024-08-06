package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.getVersionConfig;

import com.omgservers.schema.module.tenant.GetVersionConfigRequest;
import com.omgservers.schema.module.tenant.GetVersionConfigResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionConfigMethod {

    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);
}
