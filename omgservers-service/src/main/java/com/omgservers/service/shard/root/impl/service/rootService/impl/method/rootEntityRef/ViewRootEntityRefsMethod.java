package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRootEntityRefsMethod {
    Uni<ViewRootEntityRefsResponse> execute(ShardModel shardModel, ViewRootEntityRefsRequest request);
}
