package com.omgservers.service.shard.pool.impl.service.webService.impl.api;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Pool Shard API")
@Path("/service/v1/shard/pool/request")
public interface PoolApi {

    /*
    Pool
     */

    @PUT
    @Path("/get-pool")
    Uni<GetPoolResponse> execute(GetPoolRequest request);

    @PUT
    @Path("/sync-pool")
    Uni<SyncPoolResponse> execute(SyncPoolRequest request);

    @PUT
    @Path("/delete-pool")
    Uni<DeletePoolResponse> execute(DeletePoolRequest request);

    /*
    PoolServer
     */

    @PUT
    @Path("/get-pool-server")
    Uni<GetPoolServerResponse> execute(GetPoolServerRequest request);

    @PUT
    @Path("/view-pool-servers")
    Uni<ViewPoolServerResponse> execute(ViewPoolServersRequest request);

    @PUT
    @Path("/sync-pool-server")
    Uni<SyncPoolServerResponse> execute(SyncPoolServerRequest request);

    @PUT
    @Path("/delete-pool-server")
    Uni<DeletePoolServerResponse> execute(DeletePoolServerRequest request);

    /*
    PoolContainer
     */

    @PUT
    @Path("/get-pool-server-container")
    Uni<GetPoolContainerResponse> execute(GetPoolContainerRequest request);

    @PUT
    @Path("/find-pool-server-container")
    Uni<FindPoolContainerResponse> execute(FindPoolContainerRequest request);

    @PUT
    @Path("/view-pool-server-containers")
    Uni<ViewPoolContainersResponse> execute(ViewPoolContainersRequest request);

    @PUT
    @Path("/sync-pool-server-container")
    Uni<SyncPoolContainerResponse> execute(SyncPoolContainerRequest request);

    @PUT
    @Path("/delete-pool-server-container")
    Uni<DeletePoolContainerResponse> execute(DeletePoolContainerRequest request);

    /*
    PoolRequest
     */

    @PUT
    @Path("/get-pool-request")
    Uni<GetPoolRequestResponse> execute(GetPoolRequestRequest request);

    @PUT
    @Path("/find-pool-request")
    Uni<FindPoolRequestResponse> execute(FindPoolRequestRequest request);

    @PUT
    @Path("/view-pool-requests")
    Uni<ViewPoolRequestsResponse> execute(ViewPoolRequestsRequest request);

    @PUT
    @Path("/sync-pool-request")
    Uni<SyncPoolRequestResponse> execute(SyncPoolRequestRequest request);

    @PUT
    @Path("/delete-pool-request")
    Uni<DeletePoolRequestResponse> execute(DeletePoolRequestRequest request);

    /*
    PoolState
     */

    @PUT
    @Path("/get-pool-state")
    Uni<GetPoolStateResponse> execute(GetPoolStateRequest request);

    @PUT
    @Path("/update-pool-state")
    Uni<UpdatePoolStateResponse> execute(UpdatePoolStateRequest request);
}
