package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getStageVersionId;

import com.omgservers.dto.tenant.GetStageVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetStageVersionIdShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageVersionIdMethod {

    Uni<GetStageVersionIdShardedResponse> getStageVersionId(GetStageVersionIdShardedRequest request);
}
