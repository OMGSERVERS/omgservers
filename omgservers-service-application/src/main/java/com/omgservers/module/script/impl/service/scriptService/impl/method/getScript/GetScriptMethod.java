package com.omgservers.module.script.impl.service.scriptService.impl.method.getScript;

import com.omgservers.model.dto.script.GetScriptRequest;
import com.omgservers.model.dto.script.GetScriptResponse;
import io.smallrye.mutiny.Uni;

public interface GetScriptMethod {
    Uni<GetScriptResponse> getScript(GetScriptRequest request);
}
