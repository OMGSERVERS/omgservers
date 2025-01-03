package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceClient;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/v1/entrypoint/dispatcher/request")
public interface ServiceApi {

    @PUT
    @Path("/calculate-shard")
    Uni<CalculateShardDispatcherResponse> execute(@NotNull CalculateShardDispatcherRequest request);
}
