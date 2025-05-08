package com.omgservers.service.shard.root.impl.operation.getRootModuleClient;

import java.net.URI;

public interface GetRootModuleClientOperation {
    RootModuleClient execute(URI uri);
}
