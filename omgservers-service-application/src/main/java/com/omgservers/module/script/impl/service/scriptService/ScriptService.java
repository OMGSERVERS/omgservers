package com.omgservers.module.script.impl.service.scriptService;

import com.omgservers.model.dto.script.CallScriptRequest;
import com.omgservers.model.dto.script.CallScriptResponse;
import com.omgservers.model.dto.script.DeleteScriptRequest;
import com.omgservers.model.dto.script.DeleteScriptResponse;
import com.omgservers.model.dto.script.GetScriptRequest;
import com.omgservers.model.dto.script.GetScriptResponse;
import com.omgservers.model.dto.script.SyncScriptRequest;
import com.omgservers.model.dto.script.SyncScriptResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ScriptService {

    Uni<GetScriptResponse> getScript(@Valid GetScriptRequest request);

    Uni<SyncScriptResponse> syncScript(@Valid SyncScriptRequest request);

    Uni<DeleteScriptResponse> deleteScript(@Valid DeleteScriptRequest request);

    Uni<CallScriptResponse> callScript(@Valid CallScriptRequest request);
}
