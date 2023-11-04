package com.omgservers.service.module.user.impl.service.clientService.impl.method.deleteClient;

import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.DeleteClientRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);
}
