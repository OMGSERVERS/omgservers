package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.module.pool.poolCommand.GetPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.GetPoolCommandResponse;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolCommandMethod {
    Uni<GetPoolCommandResponse> execute(GetPoolCommandRequest request);
}
