package com.omgservers.module.script.impl.service.scriptService.impl.method.callScript;

import com.omgservers.model.dto.script.CallScriptRequest;
import com.omgservers.model.dto.script.CallScriptResponse;
import io.smallrye.mutiny.Uni;

public interface CallScriptMethod {
    Uni<CallScriptResponse> callScript(CallScriptRequest request);
}
