package com.omgservers.module.user.impl.service.clientShardedService;

import com.omgservers.dto.user.DeleteClientShardedResponse;
import com.omgservers.dto.user.DeleteClientShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.dto.user.SyncClientShardedRequest;
import io.smallrye.mutiny.Uni;

public interface ClientShardedService {

    Uni<SyncClientShardedResponse> syncClient(SyncClientShardedRequest request);

    Uni<GetClientShardedResponse> getClient(GetClientShardedRequest request);

    Uni<DeleteClientShardedResponse> deleteClient(DeleteClientShardedRequest request);
}
