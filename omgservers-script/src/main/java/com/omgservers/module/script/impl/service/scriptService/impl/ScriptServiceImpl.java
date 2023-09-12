package com.omgservers.module.script.impl.service.scriptService.impl;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.script.DeleteScriptRequest;
import com.omgservers.dto.script.DeleteScriptResponse;
import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.script.SyncScriptResponse;
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
    public Uni<GetScriptResponse> getScript(GetScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetScriptRequest::validate,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::getScript,
                getScriptMethod::getScript);
    }

    @Override
    public Uni<SyncScriptResponse> syncScript(SyncScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncScriptRequest::validate,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::syncScript,
                syncScriptMethod::syncScript);
    }

    @Override
    public Uni<DeleteScriptResponse> deleteScript(DeleteScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteScriptRequest::validate,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::deleteScript,
                deleteScriptMethod::deleteScript);
    }

    @Override
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CallScriptRequest::validate,
                getScriptModuleClientOperation::getClient,
                ScriptModuleClient::callScript,
                callScriptMethod::callScript);
    }
}
