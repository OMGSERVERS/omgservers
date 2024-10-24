package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolState;

import com.omgservers.schema.module.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePoolStateMethod {
    Uni<UpdatePoolStateResponse> execute(UpdatePoolStateRequest request);
}
