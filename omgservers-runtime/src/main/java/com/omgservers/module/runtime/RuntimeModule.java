package com.omgservers.module.runtime;

import com.omgservers.module.runtime.impl.service.runtimeShardedService.RuntimeShardedService;

public interface RuntimeModule {

    RuntimeShardedService getRuntimeShardedService();
}
