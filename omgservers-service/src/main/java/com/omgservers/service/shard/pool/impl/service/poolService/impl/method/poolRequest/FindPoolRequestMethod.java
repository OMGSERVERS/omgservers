package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolRequestMethod {
    Uni<FindPoolRequestResponse> execute(FindPoolRequestRequest request);
}
