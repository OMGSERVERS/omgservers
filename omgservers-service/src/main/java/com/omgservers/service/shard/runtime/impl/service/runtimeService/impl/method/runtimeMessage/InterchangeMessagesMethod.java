package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMessagesMethod {
    Uni<InterchangeMessagesResponse> execute(ShardModel shardModel, InterchangeMessagesRequest request);
}
