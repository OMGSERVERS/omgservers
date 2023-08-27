package com.omgservers.module.version.impl.service.versionShardedService.impl.method.getVersion;

import com.omgservers.dto.version.GetVersionShardedRequest;
import com.omgservers.dto.version.GetVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {

    Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request);
}
