package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.findRootEntityRef;

import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindRootEntityRefMethod {
    Uni<FindRootEntityRefResponse> findRootEntityRef(FindRootEntityRefRequest request);
}
