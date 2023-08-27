package com.omgservers.module.version.impl.service.versionShardedService.impl.method.getStageConfig;

import com.omgservers.dto.version.GetStageConfigShardedRequest;
import com.omgservers.dto.version.GetStageConfigShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageConfigMethod {

    Uni<GetStageConfigShardedResponse> getStageConfig(GetStageConfigShardedRequest request);
}
