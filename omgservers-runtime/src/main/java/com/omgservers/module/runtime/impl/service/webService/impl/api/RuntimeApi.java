package com.omgservers.module.runtime.impl.service.webService.impl.api;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/runtime-api/v1/request")
public interface RuntimeApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request);

    @PUT
    @Path("/sync-runtime-command")
    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);

    @PUT
    @Path("/delete-runtime-command")
    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request);

    @PUT
    @Path("/sync-runtime-grant")
    Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(SyncRuntimeGrantRequest request);

    @PUT
    @Path("/delete-runtime-grant")
    Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(DeleteRuntimeGrantRequest request);

    @PUT
    @Path("/do-runtime-update")
    Uni<DoRuntimeUpdateResponse> doRuntimeUpdate(DoRuntimeUpdateRequest request);
}
