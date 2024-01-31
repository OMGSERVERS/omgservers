package com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientRuntime;

import com.omgservers.model.dto.client.DeleteClientRuntimeRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientRuntimeMethod {
    Uni<DeleteClientRuntimeResponse> deleteClientRuntime(DeleteClientRuntimeRequest request);
}
