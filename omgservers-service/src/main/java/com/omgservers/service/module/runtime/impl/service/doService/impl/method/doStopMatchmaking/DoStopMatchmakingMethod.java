package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doStopMatchmaking;

import com.omgservers.model.dto.runtime.DoStopMatchmakingRequest;
import com.omgservers.model.dto.runtime.DoStopMatchmakingResponse;
import io.smallrye.mutiny.Uni;

public interface DoStopMatchmakingMethod {
    Uni<DoStopMatchmakingResponse> doStopMatchmaking(DoStopMatchmakingRequest request);
}
