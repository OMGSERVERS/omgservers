package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceAnonymousClient;

import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/service/v1/entrypoint/dispatcher/request")
public interface ServiceAnonymousApi {

    @POST
    @Path("/create-token")
    Uni<CreateTokenDispatcherResponse> execute(@NotNull CreateTokenDispatcherRequest request);
}
