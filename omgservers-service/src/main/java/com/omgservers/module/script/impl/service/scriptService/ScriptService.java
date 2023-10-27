package com.omgservers.module.script.impl.service.scriptService;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.script.DeleteScriptRequest;
import com.omgservers.dto.script.DeleteScriptResponse;
import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.script.SyncScriptResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ScriptService {

    Uni<GetScriptResponse> getScript(@Valid GetScriptRequest request);

    Uni<SyncScriptResponse> syncScript(@Valid SyncScriptRequest request);

    Uni<DeleteScriptResponse> deleteScript(@Valid DeleteScriptRequest request);

    Uni<CallScriptResponse> callScript(@Valid CallScriptRequest request);
}
