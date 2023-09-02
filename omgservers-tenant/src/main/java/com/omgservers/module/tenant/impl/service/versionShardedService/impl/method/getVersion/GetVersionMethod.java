package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getVersion;

import com.omgservers.dto.tenant.GetVersionShardedRequest;
import com.omgservers.dto.tenant.GetVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {

    Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request);
}
