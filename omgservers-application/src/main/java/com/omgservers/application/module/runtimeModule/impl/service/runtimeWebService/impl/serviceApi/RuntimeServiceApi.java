package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.dto.runtimeModule.DeleteCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateRoutedRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/service-api/runtime-api/v1/request")
public interface RuntimeServiceApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeRoutedRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeRoutedRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeRoutedRequest request);

    @PUT
    @Path("/sync-command")
    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandRoutedRequest request);

    @PUT
    @Path("/delete-command")
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandRoutedRequest request);

    @PUT
    @Path("/do-update")
    Uni<Void> doUpdate(DoUpdateRoutedRequest request);
}
