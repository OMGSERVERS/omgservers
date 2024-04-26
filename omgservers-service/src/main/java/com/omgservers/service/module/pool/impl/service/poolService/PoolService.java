package com.omgservers.service.module.pool.impl.service.poolService;

import com.omgservers.model.dto.pool.pool.DeletePoolRequest;
import com.omgservers.model.dto.pool.pool.DeletePoolResponse;
import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.GetPoolResponse;
import com.omgservers.model.dto.pool.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.pool.SyncPoolResponse;
import com.omgservers.model.dto.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.model.dto.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.GetPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.GetPoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.FindPoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.SyncPoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.model.dto.pool.poolServerContainer.ViewPoolServerContainersResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PoolService {

    Uni<GetPoolResponse> getPool(@Valid GetPoolRequest request);

    Uni<SyncPoolResponse> syncPool(@Valid SyncPoolRequest request);

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
