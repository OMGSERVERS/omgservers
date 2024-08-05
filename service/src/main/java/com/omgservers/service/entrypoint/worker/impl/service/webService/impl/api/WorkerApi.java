package com.omgservers.service.entrypoint.worker.impl.service.webService.impl.api;

import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerRequest;
import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerResponse;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerRequest;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerResponse;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerRequest;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Worker Entrypoint API")
@Path("/omgservers/v1/entrypoint/worker/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.WORKER_SECURITY_SCHEMA)
public interface WorkerApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenWorkerResponse> createToken(@NotNull CreateTokenWorkerRequest request);

    @PUT
    @Path("/get-config")
    Uni<GetConfigWorkerResponse> getConfig(@NotNull GetConfigWorkerRequest request);

    @PUT
    @Path("/interchange")
    Uni<InterchangeWorkerResponse> interchange(@NotNull InterchangeWorkerRequest request);
}
