package com.omgservers.service.module.root.impl.service.rootService.impl.method.syncRoot;

import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRootMethod {

    Uni<SyncRootResponse> syncRoot(SyncRootRequest request);
}
