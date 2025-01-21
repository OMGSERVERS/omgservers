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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Pool Shard API")
@Path("/service/v1/shard/pool/request")
public interface PoolApi {

    /*
    Pool
     */

    @POST
    @Path("/get-pool")
    Uni<GetPoolResponse> execute(GetPoolRequest request);

    @POST
    @Path("/sync-pool")
    Uni<SyncPoolResponse> execute(SyncPoolRequest request);

    @POST
    @Path("/delete-pool")
    Uni<DeletePoolResponse> execute(DeletePoolRequest request);

    /*
    PoolServer
     */

    @POST
    @Path("/get-pool-server")
    Uni<GetPoolServerResponse> execute(GetPoolServerRequest request);

    @POST
    @Path("/view-pool-servers")
    Uni<ViewPoolServerResponse> execute(ViewPoolServersRequest request);

    @POST
    @Path("/sync-pool-server")
    Uni<SyncPoolServerResponse> execute(SyncPoolServerRequest request);

    @POST
    @Path("/delete-pool-server")
    Uni<DeletePoolServerResponse> execute(DeletePoolServerRequest request);

    /*
    PoolContainer
     */

    @POST
    @Path("/get-pool-server-container")
    Uni<GetPoolContainerResponse> execute(GetPoolContainerRequest request);

    @POST
    @Path("/find-pool-server-container")
    Uni<FindPoolContainerResponse> execute(FindPoolContainerRequest request);

    @POST
    @Path("/view-pool-server-containers")
    Uni<ViewPoolContainersResponse> execute(ViewPoolContainersRequest request);

    @POST
    @Path("/sync-pool-server-container")
    Uni<SyncPoolContainerResponse> execute(SyncPoolContainerRequest request);

    @POST
    @Path("/delete-pool-server-container")
    Uni<DeletePoolContainerResponse> execute(DeletePoolContainerRequest request);

    /*
    PoolRequest
     */

    @POST
    @Path("/get-pool-request")
    Uni<GetPoolRequestResponse> execute(GetPoolRequestRequest request);

    @POST
    @Path("/find-pool-request")
    Uni<FindPoolRequestResponse> execute(FindPoolRequestRequest request);

    @POST
    @Path("/view-pool-requests")
    Uni<ViewPoolRequestsResponse> execute(ViewPoolRequestsRequest request);

    @POST
    @Path("/sync-pool-request")
    Uni<SyncPoolRequestResponse> execute(SyncPoolRequestRequest request);

    @POST
    @Path("/delete-pool-request")
    Uni<DeletePoolRequestResponse> execute(DeletePoolRequestRequest request);

    /*
    PoolState
     */

    @POST
    @Path("/get-pool-state")
    Uni<GetPoolStateResponse> execute(GetPoolStateRequest request);

    @POST
    @Path("/update-pool-state")
    Uni<UpdatePoolStateResponse> execute(UpdatePoolStateRequest request);
}
