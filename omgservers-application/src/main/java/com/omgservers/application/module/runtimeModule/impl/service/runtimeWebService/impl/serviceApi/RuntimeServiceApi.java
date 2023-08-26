package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.dto.runtimeModule.DeleteCommandShardRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandShardRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/runtime-api/v1/request")
public interface RuntimeServiceApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardRequest request);

    @PUT
    @Path("/sync-command")
    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardRequest request);

    @PUT
    @Path("/delete-command")
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardRequest request);

    @PUT
    @Path("/do-update")
    Uni<Void> doUpdate(DoUpdateShardRequest request);
}
