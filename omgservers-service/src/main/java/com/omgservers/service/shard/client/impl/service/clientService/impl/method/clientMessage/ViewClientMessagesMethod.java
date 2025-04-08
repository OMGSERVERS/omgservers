package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.client.clientMessage.ViewClientMessagesRequest;
import com.omgservers.schema.module.client.clientMessage.ViewClientMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientMessagesMethod {
    Uni<ViewClientMessagesResponse> execute(ShardModel shardModel, ViewClientMessagesRequest request);
}
