
package com.omgservers.service.entrypoint.dispatcher.impl.service.webService.impl.dispatcherApi;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Dispatcher Entrypoint API")
@Path("/service/v1/entrypoint/dispatcher/request")
public interface DispatcherApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenDispatcherResponse> execute(@NotNull CreateTokenDispatcherRequest request);

    @PUT
    @Path("/calculate-shard")
    Uni<CalculateShardDispatcherResponse> execute(@NotNull CalculateShardDispatcherRequest request);
}
