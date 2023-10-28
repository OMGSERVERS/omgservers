package com.omgservers.module.script.impl.service.webService.impl;

import com.omgservers.model.dto.script.CallScriptRequest;
import com.omgservers.model.dto.script.CallScriptResponse;
import com.omgservers.model.dto.script.DeleteScriptRequest;
import com.omgservers.model.dto.script.DeleteScriptResponse;
import com.omgservers.model.dto.script.GetScriptRequest;
import com.omgservers.model.dto.script.GetScriptResponse;
import com.omgservers.model.dto.script.SyncScriptRequest;
import com.omgservers.model.dto.script.SyncScriptResponse;
import com.omgservers.module.script.impl.service.scriptService.ScriptService;
import com.omgservers.module.script.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final ScriptService scriptService;

    @Override
    public Uni<GetScriptResponse> getScript(GetScriptRequest request) {
        return scriptService.getScript(request);
    }

    @Override
    public Uni<SyncScriptResponse> syncScript(SyncScriptRequest request) {
        return scriptService.syncScript(request);
    }

    @Override
    public Uni<DeleteScriptResponse> deleteScript(DeleteScriptRequest request) {
        return scriptService.deleteScript(request);
    }

    @Override
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        return scriptService.callScript(request);
    }
}
