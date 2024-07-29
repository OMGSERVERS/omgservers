package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.deleteClientMessages;

import com.omgservers.schema.module.client.DeleteClientMessagesRequest;
import com.omgservers.schema.module.client.DeleteClientMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMessagesMethod {
    Uni<DeleteClientMessagesResponse> deleteClientMessages(DeleteClientMessagesRequest request);
}
