package com.omgservers.module.script.impl.service.scriptService.impl;

import com.omgservers.model.dto.script.CallScriptRequest;
import com.omgservers.model.dto.script.CallScriptResponse;
import com.omgservers.model.dto.script.DeleteScriptRequest;
import com.omgservers.model.dto.script.DeleteScriptResponse;
import com.omgservers.model.dto.script.GetScriptRequest;
import com.omgservers.model.dto.script.GetScriptResponse;
import com.omgservers.model.dto.script.SyncScriptRequest;
import com.omgservers.model.dto.script.SyncScriptResponse;
import com.omgservers.module.script.impl.operation.getScriptModuleClient.GetScriptModuleClientOperation;
import com.omgservers.module.script.impl.operation.getScriptModuleClient.ScriptModuleClient;
import com.omgservers.module.script.impl.service.scriptService.ScriptService;
import com.omgservers.module.script.impl.service.scriptService.impl.method.callScript.CallScriptMethod;
import com.omgservers.module.script.impl.service.scriptService.impl.method.deleteScript.DeleteScriptMethod;
import com.omgservers.module.script.impl.service.scriptService.impl.method.getScript.GetScriptMethod;
import com.omgservers.module.script.impl.service.scriptService.impl.method.syncScript.SyncScriptMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScriptServiceImpl implements ScriptService {

    final DeleteScriptMethod deleteScriptMethod;
    final SyncScriptMethod syncScriptMethod;
    final CallScriptMethod callScriptMethod;
    final GetScriptMethod getScriptMethod;

    final GetScriptModuleClientOperation getScriptModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetScriptResponse> getScript(@Valid final GetScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::getScript,
                getScriptMethod::getScript);
    }

    @Override
    public Uni<SyncScriptResponse> syncScript(@Valid final SyncScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::syncScript,
                syncScriptMethod::syncScript);
    }

    @Override
    public Uni<DeleteScriptResponse> deleteScript(@Valid final DeleteScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::deleteScript,
                deleteScriptMethod::deleteScript);
    }

    @Override
    public Uni<CallScriptResponse> callScript(@Valid final CallScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::callScript,
                callScriptMethod::callScript);
    }
}
