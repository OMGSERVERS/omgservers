package com.omgservers.module.script.impl.service.webService.impl.api;

import com.omgservers.model.dto.script.CallScriptRequest;
import com.omgservers.model.dto.script.CallScriptResponse;
import com.omgservers.model.dto.script.DeleteScriptRequest;
import com.omgservers.model.dto.script.DeleteScriptResponse;
import com.omgservers.model.dto.script.GetScriptRequest;
import com.omgservers.model.dto.script.GetScriptResponse;
import com.omgservers.model.dto.script.SyncScriptRequest;
import com.omgservers.model.dto.script.SyncScriptResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/script-api/v1/request")
public interface ScriptApi {

    @PUT
    @Path("/get-script")
    Uni<GetScriptResponse> getScript(GetScriptRequest request);

    default GetScriptResponse getScript(long timeout, GetScriptRequest request) {
        return getScript(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-script")
    Uni<SyncScriptResponse> syncScript(SyncScriptRequest request);

    default SyncScriptResponse syncScript(long timeout, SyncScriptRequest request) {
        return syncScript(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-script")
    Uni<DeleteScriptResponse> deleteScript(DeleteScriptRequest request);

    default DeleteScriptResponse deleteScript(long timeout, DeleteScriptRequest request) {
        return deleteScript(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/call-script")
    Uni<CallScriptResponse> callScript(CallScriptRequest request);

    default CallScriptResponse callScript(long timeout, CallScriptRequest request) {
        return callScript(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
