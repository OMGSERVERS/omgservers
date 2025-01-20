package com.omgservers.service.shard.pool.impl.service.webService.impl;

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
import com.omgservers.service.shard.pool.impl.service.poolService.PoolService;
import com.omgservers.service.shard.pool.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final PoolService poolService;

    /*
    Pool
     */

    @Override
    public Uni<GetPoolResponse> execute(final GetPoolRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<SyncPoolResponse> execute(final SyncPoolRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<DeletePoolResponse> execute(final DeletePoolRequest request) {
        return poolService.execute(request);
    }

    /*
    PoolServer
     */

    @Override
    public Uni<GetPoolServerResponse> execute(final GetPoolServerRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<ViewPoolServerResponse> execute(final ViewPoolServersRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<SyncPoolServerResponse> execute(final SyncPoolServerRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<DeletePoolServerResponse> execute(final DeletePoolServerRequest request) {
        return poolService.execute(request);
    }

    /*
    PoolContainer
     */

    @Override
    public Uni<GetPoolContainerResponse> execute(final GetPoolContainerRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<FindPoolContainerResponse> execute(final FindPoolContainerRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<ViewPoolContainersResponse> execute(final ViewPoolContainersRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<SyncPoolContainerResponse> execute(final SyncPoolContainerRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<DeletePoolContainerResponse> execute(final DeletePoolContainerRequest request) {
        return poolService.execute(request);
    }

    /*
    PoolRequest
     */

    @Override
    public Uni<GetPoolRequestResponse> execute(final GetPoolRequestRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<FindPoolRequestResponse> execute(final FindPoolRequestRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<ViewPoolRequestsResponse> execute(final ViewPoolRequestsRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<SyncPoolRequestResponse> execute(final SyncPoolRequestRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<DeletePoolRequestResponse> execute(final DeletePoolRequestRequest request) {
        return poolService.execute(request);
    }

    /*
    PoolState
     */

    @Override
    public Uni<GetPoolStateResponse> execute(GetPoolStateRequest request) {
        return poolService.execute(request);
    }

    @Override
    public Uni<UpdatePoolStateResponse> execute(UpdatePoolStateRequest request) {
        return poolService.execute(request);
    }
}
