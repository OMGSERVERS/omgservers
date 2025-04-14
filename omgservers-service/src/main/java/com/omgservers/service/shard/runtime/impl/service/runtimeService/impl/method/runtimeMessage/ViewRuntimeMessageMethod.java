package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeMessage.ViewRuntimeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.ViewRuntimeMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeMessageMethod {
    Uni<ViewRuntimeMessagesResponse> execute(ShardModel shardModel, ViewRuntimeMessagesRequest request);
}
