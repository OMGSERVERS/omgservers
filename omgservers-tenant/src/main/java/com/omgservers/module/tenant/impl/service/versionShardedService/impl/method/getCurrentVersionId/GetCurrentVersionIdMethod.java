package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getCurrentVersionId;

import com.omgservers.dto.tenant.GetCurrentVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetCurrentVersionIdShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetCurrentVersionIdMethod {

    Uni<GetCurrentVersionIdShardedResponse> getCurrentVersionId(GetCurrentVersionIdShardedRequest request);
}
