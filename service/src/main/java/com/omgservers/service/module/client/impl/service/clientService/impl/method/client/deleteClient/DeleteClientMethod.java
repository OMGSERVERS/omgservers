package com.omgservers.service.module.client.impl.service.clientService.impl.method.client.deleteClient;

import com.omgservers.schema.module.client.DeleteClientRequest;
import com.omgservers.schema.module.client.DeleteClientResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);
}
