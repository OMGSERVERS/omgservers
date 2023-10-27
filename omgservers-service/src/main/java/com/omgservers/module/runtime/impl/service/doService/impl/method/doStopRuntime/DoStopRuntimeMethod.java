package com.omgservers.module.runtime.impl.service.doService.impl.method.doStopRuntime;

import com.omgservers.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.dto.runtime.DoStopRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface DoStopRuntimeMethod {
    Uni<DoStopRuntimeResponse> doStopRuntime(DoStopRuntimeRequest request);
}
