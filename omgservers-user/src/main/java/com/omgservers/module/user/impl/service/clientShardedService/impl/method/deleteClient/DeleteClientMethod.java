package com.omgservers.module.user.impl.service.clientShardedService.impl.method.deleteClient;

import com.omgservers.dto.user.DeleteClientShardResponse;
import com.omgservers.dto.user.DeleteClientShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientShardResponse> deleteClient(DeleteClientShardedRequest request);
}
