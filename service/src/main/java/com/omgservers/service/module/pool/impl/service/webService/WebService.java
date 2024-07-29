package com.omgservers.service.module.pool.impl.service.webService;

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

public interface WebService {

    Uni<GetPoolResponse> getPool(GetPoolRequest request);

    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);

    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);

    Uni<GetPoolServerResponse> getPoolServer(GetPoolServerRequest request);

    Uni<ViewPoolServerResponse> viewPoolServers(ViewPoolServersRequest request);

    Uni<SyncPoolServerResponse> syncPoolServer(SyncPoolServerRequest request);

    Uni<DeletePoolServerResponse> deletePoolServer(DeletePoolServerRequest request);

    Uni<GetPoolRequestResponse> getPoolRequest(GetPoolRequestRequest request);

    Uni<FindPoolRequestResponse> findPoolRequest(FindPoolRequestRequest request);

    Uni<ViewPoolRequestsResponse> viewPoolRequests(ViewPoolRequestsRequest request);

    Uni<SyncPoolRequestResponse> syncPoolRequest(SyncPoolRequestRequest request);

    Uni<DeletePoolRequestResponse> deletePoolRequest(DeletePoolRequestRequest request);

    Uni<GetPoolServerContainerResponse> getPoolServerContainer(GetPoolServerContainerRequest request);

    Uni<FindPoolServerContainerResponse> findPoolServerContainer(FindPoolServerContainerRequest request);

    Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(ViewPoolServerContainersRequest request);

    Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(SyncPoolServerContainerRequest request);

    Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(DeletePoolServerContainerRequest request);
}
