package com.omgservers.service.shard.runtime.impl.operation.getRuntimeModuleClient;

import java.net.URI;

public interface GetRuntimeModuleClientOperation {
    RuntimeModuleClient execute(URI uri);
}
