package com.omgservers.module.script.impl.service.webService;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.script.DeleteScriptRequest;
import com.omgservers.dto.script.DeleteScriptResponse;
import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.script.SyncScriptResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetScriptResponse> getScript(GetScriptRequest request);

    Uni<SyncScriptResponse> syncScript(SyncScriptRequest request);

    Uni<DeleteScriptResponse> deleteScript(DeleteScriptRequest request);

    Uni<CallScriptResponse> callScript(CallScriptRequest request);
}
