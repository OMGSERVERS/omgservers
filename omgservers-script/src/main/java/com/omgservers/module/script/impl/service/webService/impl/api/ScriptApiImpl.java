package com.omgservers.module.script.impl.service.webService.impl.api;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.script.DeleteScriptRequest;
import com.omgservers.dto.script.DeleteScriptResponse;
import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.script.SyncScriptResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.script.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScriptApiImpl implements ScriptApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetScriptResponse> getScript(GetScriptRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getScript);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncScriptResponse> syncScript(SyncScriptRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncScript);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteScriptResponse> deleteScript(DeleteScriptRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteScript);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::callScript);
    }
}
