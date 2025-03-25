package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand;

import com.omgservers.schema.module.pool.poolCommand.ViewPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.ViewPoolCommandResponse;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolCommandsMethod {
    Uni<ViewPoolCommandResponse> execute(ViewPoolCommandRequest request);
}
