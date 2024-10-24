package com.omgservers.service.module.pool.impl.service.poolService;

import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
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
import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.module.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.module.pool.poolState.GetPoolStateResponse;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateResponse;
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
    PoolServer
     */

    Uni<GetPoolServerResponse> execute(@Valid GetPoolServerRequest request);

    Uni<ViewPoolServerResponse> execute(@Valid ViewPoolServersRequest request);

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
    PoolRequest
     */

    Uni<GetPoolRequestResponse> execute(@Valid GetPoolRequestRequest request);

    Uni<FindPoolRequestResponse> execute(@Valid FindPoolRequestRequest request);

    Uni<ViewPoolRequestsResponse> execute(@Valid ViewPoolRequestsRequest request);

    Uni<SyncPoolRequestResponse> execute(@Valid SyncPoolRequestRequest request);

    Uni<SyncPoolRequestResponse> executeWithIdempotency(@Valid SyncPoolRequestRequest request);

    Uni<DeletePoolRequestResponse> execute(@Valid DeletePoolRequestRequest request);

    /*
    PoolState
     */

    Uni<GetPoolStateResponse> execute(@Valid GetPoolStateRequest request);

    Uni<UpdatePoolStateResponse> execute(@Valid UpdatePoolStateRequest request);
}
