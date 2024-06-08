package com.omgservers.service.module.root.impl.service.rootService.impl.method.root.syncRoot;

import com.omgservers.model.dto.root.root.SyncRootRequest;
import com.omgservers.model.dto.root.root.SyncRootResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRootMethod {

    Uni<SyncRootResponse> syncRoot(SyncRootRequest request);
}
