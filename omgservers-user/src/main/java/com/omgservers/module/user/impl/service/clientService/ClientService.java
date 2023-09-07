package com.omgservers.module.user.impl.service.clientService;

import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncClientRequest;
import io.smallrye.mutiny.Uni;

public interface ClientService {

    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    Uni<GetClientResponse> getClient(GetClientRequest request);

    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);
}
