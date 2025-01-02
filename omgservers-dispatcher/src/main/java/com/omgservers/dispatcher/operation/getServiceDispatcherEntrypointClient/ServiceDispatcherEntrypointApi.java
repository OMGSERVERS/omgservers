package com.omgservers.dispatcher.operation.getServiceDispatcherEntrypointClient;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/v1/entrypoint/dispatcher/request")
public interface ServiceDispatcherEntrypointApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenDispatcherResponse> execute(@NotNull CreateTokenDispatcherRequest request);

    @PUT
    @Path("/calculate-shard")
    Uni<CalculateShardDispatcherResponse> execute(@NotNull CalculateShardDispatcherRequest request);
}
