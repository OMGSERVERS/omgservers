package com.omgservers.module.runtime.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.dto.runtime.DeleteCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteCommandInternalResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeInternalResponse;
import com.omgservers.dto.runtime.SyncCommandShardedRequest;
import com.omgservers.dto.runtime.SyncCommandInternalResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/runtime-api/v1/request")
public interface RuntimeServiceApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardedRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardedRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardedRequest request);

    @PUT
    @Path("/sync-command")
    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardedRequest request);

    @PUT
    @Path("/delete-command")
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardedRequest request);

    @PUT
    @Path("/do-update")
    Uni<Void> doUpdate(DoUpdateShardedRequest request);
}
