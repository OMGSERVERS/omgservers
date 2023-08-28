package com.omgservers.module.runtime.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/runtime-api/v1/request")
public interface RuntimeServiceApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeShardedResponse> getRuntime(GetRuntimeShardedRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeShardedResponse> syncRuntime(SyncRuntimeShardedRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request);

    @PUT
    @Path("/sync-runtime-command")
    Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request);

    @PUT
    @Path("/delete-runtime-command")
    Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request);

    @PUT
    @Path("/do-runtime-update")
    Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(DoRuntimeUpdateShardedRequest request);
}
