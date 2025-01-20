package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolRequestMethod {
    Uni<DeletePoolRequestResponse> execute(DeletePoolRequestRequest request);
}
