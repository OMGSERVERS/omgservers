package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.syncRootEntityRef;

import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRootEntityRefMethod {
    Uni<SyncRootEntityRefResponse> syncRootEntityRef(SyncRootEntityRefRequest request);
}
