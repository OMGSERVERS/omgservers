package com.omgservers.service.module.pool.impl.service.poolService;

import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
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
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PoolService {

    Uni<GetPoolResponse> getPool(@Valid GetPoolRequest request);

    Uni<SyncPoolResponse> syncPool(@Valid SyncPoolRequest request);

    Uni<SyncPoolResponse> syncPoolWithIdempotency(@Valid SyncPoolRequest request);

    Uni<DeletePoolResponse> deletePool(@Valid DeletePoolRequest request);

    Uni<GetPoolServerResponse> getPoolServer(@Valid GetPoolServerRequest request);

    Uni<ViewPoolServerResponse> viewPoolServers(@Valid ViewPoolServersRequest request);

    Uni<SyncPoolServerResponse> syncPoolServer(@Valid SyncPoolServerRequest request);

    Uni<SyncPoolServerResponse> syncPoolServerWithIdempotency(@Valid SyncPoolServerRequest request);

    Uni<DeletePoolServerResponse> deletePoolServer(@Valid DeletePoolServerRequest request);

    Uni<GetPoolRequestResponse> getPoolRequest(@Valid GetPoolRequestRequest request);

    Uni<FindPoolRequestResponse> findPoolRequest(@Valid FindPoolRequestRequest request);

    Uni<ViewPoolRequestsResponse> viewPoolRequests(@Valid ViewPoolRequestsRequest request);

    Uni<SyncPoolRequestResponse> syncPoolRequest(@Valid SyncPoolRequestRequest request);

    Uni<SyncPoolRequestResponse> syncPoolRequestWithIdempotency(@Valid SyncPoolRequestRequest request);

    Uni<DeletePoolRequestResponse> deletePoolRequest(@Valid DeletePoolRequestRequest request);

    Uni<GetPoolServerContainerResponse> getPoolServerContainer(@Valid GetPoolServerContainerRequest request);

    Uni<FindPoolServerContainerResponse> findPoolServerContainer(@Valid FindPoolServerContainerRequest request);

    Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(@Valid ViewPoolServerContainersRequest request);

    Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(@Valid SyncPoolServerContainerRequest request);

    Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(@Valid DeletePoolServerContainerRequest request);
}
