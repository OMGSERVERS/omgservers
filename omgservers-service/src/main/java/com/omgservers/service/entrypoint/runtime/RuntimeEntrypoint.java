package com.omgservers.service.entrypoint.runtime;

import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.RuntimeService;

public interface RuntimeEntrypoint {
    RuntimeService getService();
}
