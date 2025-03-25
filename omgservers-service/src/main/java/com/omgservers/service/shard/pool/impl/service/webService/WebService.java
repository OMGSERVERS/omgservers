package com.omgservers.service.shard.pool.impl.service.webService;

import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
import com.omgservers.schema.module.pool.poolCommand.DeletePoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.DeletePoolCommandResponse;
import com.omgservers.schema.module.pool.poolCommand.GetPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.GetPoolCommandResponse;
import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandResponse;
import com.omgservers.schema.module.pool.poolCommand.ViewPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.ViewPoolCommandResponse;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersResponse;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersResponse;
import com.omgservers.schema.module.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.module.pool.poolState.GetPoolStateResponse;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Pool
     */

    Uni<GetPoolResponse> execute(GetPoolRequest request);

    Uni<SyncPoolResponse> execute(SyncPoolRequest request);

    Uni<DeletePoolResponse> execute(DeletePoolRequest request);

    /*
    PoolCommand
     */

    Uni<GetPoolCommandResponse> execute(GetPoolCommandRequest request);

    Uni<ViewPoolCommandResponse> execute(ViewPoolCommandRequest request);

    Uni<SyncPoolCommandResponse> execute(SyncPoolCommandRequest request);

    Uni<DeletePoolCommandResponse> execute(DeletePoolCommandRequest request);

    /*
    PoolRequest
     */

    Uni<GetPoolRequestResponse> execute(GetPoolRequestRequest request);

    Uni<FindPoolRequestResponse> execute(FindPoolRequestRequest request);

    Uni<ViewPoolRequestsResponse> execute(ViewPoolRequestsRequest request);

    Uni<SyncPoolRequestResponse> execute(SyncPoolRequestRequest request);

    Uni<DeletePoolRequestResponse> execute(DeletePoolRequestRequest request);

    /*
    PoolServer
     */

    Uni<GetPoolServerResponse> execute(GetPoolServerRequest request);

    Uni<ViewPoolServersResponse> execute(ViewPoolServersRequest request);

    Uni<SyncPoolServerResponse> execute(SyncPoolServerRequest request);

    Uni<DeletePoolServerResponse> execute(DeletePoolServerRequest request);

    /*
    PoolContainer
     */

    Uni<GetPoolContainerResponse> execute(GetPoolContainerRequest request);

    Uni<FindPoolContainerResponse> execute(FindPoolContainerRequest request);

    Uni<ViewPoolContainersResponse> execute(ViewPoolContainersRequest request);

    Uni<SyncPoolContainerResponse> execute(SyncPoolContainerRequest request);

    Uni<DeletePoolContainerResponse> execute(DeletePoolContainerRequest request);

    /*
    PoolState
     */

    Uni<GetPoolStateResponse> execute(GetPoolStateRequest request);

    Uni<UpdatePoolStateResponse> execute(UpdatePoolStateRequest request);
}
