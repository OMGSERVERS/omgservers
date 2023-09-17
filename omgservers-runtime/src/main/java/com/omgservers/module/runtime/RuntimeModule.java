package com.omgservers.module.runtime;

import com.omgservers.module.runtime.impl.service.doService.DoService;
import com.omgservers.module.runtime.impl.service.runtimeService.RuntimeService;

public interface RuntimeModule {

    RuntimeService getRuntimeService();

    DoService getDoService();
}
