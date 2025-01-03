package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceAnonymousClient;

import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/v1/entrypoint/dispatcher/request")
public interface ServiceAnonymousApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenDispatcherResponse> execute(@NotNull CreateTokenDispatcherRequest request);
}
