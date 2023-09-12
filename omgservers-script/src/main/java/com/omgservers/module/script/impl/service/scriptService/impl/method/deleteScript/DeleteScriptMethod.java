package com.omgservers.module.script.impl.service.scriptService.impl.method.deleteScript;

import com.omgservers.dto.script.DeleteScriptRequest;
import com.omgservers.dto.script.DeleteScriptResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteScriptMethod {
    Uni<DeleteScriptResponse> deleteScript(DeleteScriptRequest request);
}
