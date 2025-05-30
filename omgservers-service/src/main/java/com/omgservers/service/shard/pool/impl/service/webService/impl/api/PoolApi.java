package com.omgservers.service.shard.pool.impl.service.webService.impl.api;

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
    PoolCommand
     */

    @POST
    @Path("/get-pool-command")
    Uni<GetPoolCommandResponse> execute(GetPoolCommandRequest request);

    @POST
    @Path("/view-pool-commands")
    Uni<ViewPoolCommandResponse> execute(ViewPoolCommandRequest request);

    @POST
    @Path("/sync-pool-command")
    Uni<SyncPoolCommandResponse> execute(SyncPoolCommandRequest request);

    @POST
    @Path("/delete-pool-command")
    Uni<DeletePoolCommandResponse> execute(DeletePoolCommandRequest request);


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
    PoolServer
     */

    @POST
    @Path("/get-pool-server")
    Uni<GetPoolServerResponse> execute(GetPoolServerRequest request);

    @POST
    @Path("/view-pool-servers")
    Uni<ViewPoolServersResponse> execute(ViewPoolServersRequest request);

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
    PoolState
     */

    @POST
    @Path("/get-pool-state")
    Uni<GetPoolStateResponse> execute(GetPoolStateRequest request);

    @POST
    @Path("/update-pool-state")
    Uni<UpdatePoolStateResponse> execute(UpdatePoolStateRequest request);
}
