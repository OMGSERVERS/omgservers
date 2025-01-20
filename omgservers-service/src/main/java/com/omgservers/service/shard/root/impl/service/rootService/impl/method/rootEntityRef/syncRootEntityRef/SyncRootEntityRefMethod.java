package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.syncRootEntityRef;

import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRootEntityRefMethod {
    Uni<SyncRootEntityRefResponse> syncRootEntityRef(SyncRootEntityRefRequest request);
}
