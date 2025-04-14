package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMessageMethod {
    Uni<SyncClientMessageResponse> execute(ShardModel shardModel, SyncClientMessageRequest request);
}
