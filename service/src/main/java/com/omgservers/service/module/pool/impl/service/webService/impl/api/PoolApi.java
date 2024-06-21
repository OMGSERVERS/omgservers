package com.omgservers.service.module.pool.impl.service.webService.impl.api;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Pool Module API")
@Path("/omgservers/v1/module/pool/request")
public interface PoolApi {

    @PUT
    @Path("/get-pool")
    Uni<GetPoolResponse> getPool(GetPoolRequest request);

    @PUT
    @Path("/sync-pool")
    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);

    @PUT
    @Path("/delete-pool")
    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);

    @PUT
    @Path("/get-pool-server")
    Uni<GetPoolServerResponse> getPoolServer(GetPoolServerRequest request);

    @PUT
    @Path("/view-pool-servers")
    Uni<ViewPoolServerResponse> viewPoolServers(ViewPoolServersRequest request);

    @PUT
    @Path("/sync-pool-server")
    Uni<SyncPoolServerResponse> syncPoolServer(SyncPoolServerRequest request);

    @PUT
    @Path("/delete-pool-server")
    Uni<DeletePoolServerResponse> deletePoolServer(DeletePoolServerRequest request);

    @PUT
    @Path("/get-pool-request")
    Uni<GetPoolRequestResponse> getPoolRequest(GetPoolRequestRequest request);

    @PUT
    @Path("/find-pool-request")
    Uni<FindPoolRequestResponse> findPoolRequest(FindPoolRequestRequest request);

    @PUT
    @Path("/view-pool-requests")
    Uni<ViewPoolRequestsResponse> viewPoolRequests(ViewPoolRequestsRequest request);

    @PUT
    @Path("/sync-pool-request")
    Uni<SyncPoolRequestResponse> syncPoolRequest(SyncPoolRequestRequest request);

    @PUT
    @Path("/delete-pool-request")
    Uni<DeletePoolRequestResponse> deletePoolRequest(DeletePoolRequestRequest request);

    @PUT
    @Path("/get-pool-server-container")
    Uni<GetPoolServerContainerResponse> getPoolServerContainer(GetPoolServerContainerRequest request);

    @PUT
    @Path("/find-pool-server-container")
    Uni<FindPoolServerContainerResponse> findPoolServerContainer(FindPoolServerContainerRequest request);

    @PUT
    @Path("/view-pool-server-containers")
    Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(ViewPoolServerContainersRequest request);

    @PUT
    @Path("/sync-pool-server-container")
    Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(SyncPoolServerContainerRequest request);

    @PUT
    @Path("/delete-pool-server-container")
    Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(DeletePoolServerContainerRequest request);
}
