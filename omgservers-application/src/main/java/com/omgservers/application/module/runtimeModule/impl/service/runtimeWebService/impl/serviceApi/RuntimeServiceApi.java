package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncRuntimeInternalResponse;
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
}
