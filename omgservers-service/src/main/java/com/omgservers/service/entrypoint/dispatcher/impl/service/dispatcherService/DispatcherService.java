package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<CreateTokenDispatcherResponse> execute(@Valid CreateTokenDispatcherRequest request);

    Uni<CalculateShardDispatcherResponse> execute(@Valid CalculateShardDispatcherRequest request);
}
