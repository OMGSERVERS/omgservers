package com.omgservers.service.entrypoint.dispatcher.impl.service.webService;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenDispatcherResponse> execute(CreateTokenDispatcherRequest request);

    Uni<CalculateShardDispatcherResponse> execute(CalculateShardDispatcherRequest request);
}
