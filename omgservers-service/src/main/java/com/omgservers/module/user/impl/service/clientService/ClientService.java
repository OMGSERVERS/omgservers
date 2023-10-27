package com.omgservers.module.user.impl.service.clientService;

import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncClientRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ClientService {

    Uni<SyncClientResponse> syncClient(@Valid SyncClientRequest request);

    Uni<GetClientResponse> getClient(@Valid GetClientRequest request);

    Uni<DeleteClientResponse> deleteClient(@Valid DeleteClientRequest request);
}
