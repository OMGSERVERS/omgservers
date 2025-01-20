package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root.syncRoot;

import com.omgservers.schema.module.root.root.SyncRootRequest;
import com.omgservers.schema.module.root.root.SyncRootResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRootMethod {

    Uni<SyncRootResponse> syncRoot(SyncRootRequest request);
}
