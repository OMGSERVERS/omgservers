package com.omgservers.service.shard.runtime;

import com.omgservers.service.shard.runtime.impl.service.runtimeService.RuntimeService;

public interface RuntimeShard {

    RuntimeService getService();
}
