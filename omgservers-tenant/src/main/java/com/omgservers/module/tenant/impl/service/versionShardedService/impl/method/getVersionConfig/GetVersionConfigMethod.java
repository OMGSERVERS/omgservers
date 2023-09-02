package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getVersionConfig;

import com.omgservers.dto.tenant.GetVersionConfigShardedRequest;
import com.omgservers.dto.tenant.GetVersionConfigShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionConfigMethod {

    Uni<GetVersionConfigShardedResponse> getVersionConfig(GetVersionConfigShardedRequest request);
}
