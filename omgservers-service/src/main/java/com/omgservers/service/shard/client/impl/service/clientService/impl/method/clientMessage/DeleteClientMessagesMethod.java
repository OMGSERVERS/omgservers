package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.client.clientMessage.DeleteClientMessagesRequest;
import com.omgservers.schema.module.client.clientMessage.DeleteClientMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMessagesMethod {
    Uni<DeleteClientMessagesResponse> execute(ShardModel shardModel, DeleteClientMessagesRequest request);
}
