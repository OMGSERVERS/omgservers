package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMessagesMethod {
    Uni<DeleteRuntimeMessagesResponse> execute(ShardModel shardModel, DeleteRuntimeMessagesRequest request);
}
