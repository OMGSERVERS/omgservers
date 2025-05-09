package com.omgservers.service.shard.pool.impl.service.poolService;

import com.omgservers.schema.shard.pool.pool.DeletePoolRequest;
import com.omgservers.schema.shard.pool.pool.DeletePoolResponse;
import com.omgservers.schema.shard.pool.pool.GetPoolRequest;
import com.omgservers.schema.shard.pool.pool.GetPoolResponse;
import com.omgservers.schema.shard.pool.pool.SyncPoolRequest;
import com.omgservers.schema.shard.pool.pool.SyncPoolResponse;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandResponse;
import com.omgservers.schema.shard.pool.poolCommand.GetPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.GetPoolCommandResponse;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandResponse;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandResponse;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.SyncPoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.shard.pool.poolContainer.ViewPoolContainersResponse;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.shard.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersResponse;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateResponse;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PoolService {

    /*
    Pool
     */

    Uni<GetPoolResponse> execute(@Valid GetPoolRequest request);

    Uni<SyncPoolResponse> execute(@Valid SyncPoolRequest request);

    Uni<SyncPoolResponse> executeWithIdempotency(@Valid SyncPoolRequest request);

    Uni<DeletePoolResponse> execute(@Valid DeletePoolRequest request);

    /*
    PoolCommand
     */

    Uni<GetPoolCommandResponse> execute(@Valid GetPoolCommandRequest request);

    Uni<ViewPoolCommandResponse> execute(@Valid ViewPoolCommandRequest request);

    Uni<SyncPoolCommandResponse> execute(@Valid SyncPoolCommandRequest request);

    Uni<SyncPoolCommandResponse> executeWithIdempotency(@Valid SyncPoolCommandRequest request);

    Uni<DeletePoolCommandResponse> execute(@Valid DeletePoolCommandRequest request);

    /*
    PoolRequest
     */

    Uni<GetPoolRequestResponse> execute(@Valid GetPoolRequestRequest request);

    Uni<FindPoolRequestResponse> execute(@Valid FindPoolRequestRequest request);

    Uni<ViewPoolRequestsResponse> execute(@Valid ViewPoolRequestsRequest request);

    Uni<SyncPoolRequestResponse> execute(@Valid SyncPoolRequestRequest request);

    Uni<SyncPoolRequestResponse> executeWithIdempotency(@Valid SyncPoolRequestRequest request);

    Uni<DeletePoolRequestResponse> execute(@Valid DeletePoolRequestRequest request);

    /*
    PoolServer
     */

    Uni<GetPoolServerResponse> execute(@Valid GetPoolServerRequest request);

    Uni<ViewPoolServersResponse> execute(@Valid ViewPoolServersRequest request);

    Uni<SyncPoolServerResponse> execute(@Valid SyncPoolServerRequest request);

    Uni<SyncPoolServerResponse> executeWithIdempotency(@Valid SyncPoolServerRequest request);

    Uni<DeletePoolServerResponse> execute(@Valid DeletePoolServerRequest request);

    /*
    PoolContainer
     */

    Uni<GetPoolContainerResponse> execute(@Valid GetPoolContainerRequest request);

    Uni<FindPoolContainerResponse> execute(@Valid FindPoolContainerRequest request);

    Uni<ViewPoolContainersResponse> execute(@Valid ViewPoolContainersRequest request);

    Uni<SyncPoolContainerResponse> execute(@Valid SyncPoolContainerRequest request);

    Uni<SyncPoolContainerResponse> executeWithIdempotency(@Valid SyncPoolContainerRequest request);

    Uni<DeletePoolContainerResponse> execute(@Valid DeletePoolContainerRequest request);

    /*
    PoolState
     */

    Uni<GetPoolStateResponse> execute(@Valid GetPoolStateRequest request);

    Uni<UpdatePoolStateResponse> execute(@Valid UpdatePoolStateRequest request);
}
