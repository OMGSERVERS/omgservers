package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.client.clientMessage.InterchangeMessagesRequest;
import com.omgservers.schema.module.client.clientMessage.InterchangeMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMessagesMethod {
    Uni<InterchangeMessagesResponse> execute(ShardModel shardModel, InterchangeMessagesRequest request);
}
