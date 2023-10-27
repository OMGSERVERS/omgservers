package com.omgservers.module.user.impl.service.clientService.impl.method.deleteClient;

import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);
}
