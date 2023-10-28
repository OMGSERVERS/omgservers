package com.omgservers.module.script.impl.service.webService;

import com.omgservers.model.dto.script.CallScriptRequest;
import com.omgservers.model.dto.script.CallScriptResponse;
import com.omgservers.model.dto.script.DeleteScriptRequest;
import com.omgservers.model.dto.script.DeleteScriptResponse;
import com.omgservers.model.dto.script.GetScriptRequest;
import com.omgservers.model.dto.script.GetScriptResponse;
import com.omgservers.model.dto.script.SyncScriptRequest;
import com.omgservers.model.dto.script.SyncScriptResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetScriptResponse> getScript(GetScriptRequest request);

    Uni<SyncScriptResponse> syncScript(SyncScriptRequest request);

    Uni<DeleteScriptResponse> deleteScript(DeleteScriptRequest request);

    Uni<CallScriptResponse> callScript(CallScriptRequest request);
}
