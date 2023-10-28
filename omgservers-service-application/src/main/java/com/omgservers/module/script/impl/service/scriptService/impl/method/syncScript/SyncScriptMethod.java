package com.omgservers.module.script.impl.service.scriptService.impl.method.syncScript;

import com.omgservers.model.dto.script.SyncScriptRequest;
import com.omgservers.model.dto.script.SyncScriptResponse;
import io.smallrye.mutiny.Uni;

public interface SyncScriptMethod {
    Uni<SyncScriptResponse> syncScript(SyncScriptRequest request);
}