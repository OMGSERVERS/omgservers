package com.omgservers.module.user.impl.service.clientService;

import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.SyncClientResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ClientService {

    Uni<SyncClientResponse> syncClient(@Valid SyncClientRequest request);

    Uni<GetClientResponse> getClient(@Valid GetClientRequest request);

    Uni<DeleteClientResponse> deleteClient(@Valid DeleteClientRequest request);
}
