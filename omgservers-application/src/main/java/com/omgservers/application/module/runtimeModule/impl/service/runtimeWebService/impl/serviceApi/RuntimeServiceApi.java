package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.dto.runtimeModule.DeleteCommandInternalRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandInternalRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/runtime-api/v1/request")
public interface RuntimeServiceApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeInternalRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request);

    @PUT
    @Path("/sync-command")
    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request);

    @PUT
    @Path("/delete-command")
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request);

    @PUT
    @Path("/do-update")
    Uni<Void> doUpdate(DoUpdateInternalRequest request);
}
