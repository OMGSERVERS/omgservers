package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMessageMethod {
    Uni<DeleteRuntimeMessageResponse> execute(ShardModel shardModel, DeleteRuntimeMessageRequest request);
}
