package com.omgservers.service.module.client.impl.service.clientService.impl.method.client.deleteClient;

import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);
}
