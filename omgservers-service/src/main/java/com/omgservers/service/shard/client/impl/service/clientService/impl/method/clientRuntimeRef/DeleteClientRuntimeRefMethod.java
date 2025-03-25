package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.module.client.clientRuntimeRef.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.DeleteClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientRuntimeRefMethod {
    Uni<DeleteClientRuntimeRefResponse> execute(DeleteClientRuntimeRefRequest request);
}
