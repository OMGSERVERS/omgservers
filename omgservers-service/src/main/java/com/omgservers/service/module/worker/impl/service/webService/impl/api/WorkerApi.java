package com.omgservers.service.module.worker.impl.service.webService.impl.api;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.InterchangeWorkerRequest;
import com.omgservers.model.dto.worker.InterchangeWorkerResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Worker API")
@Path("/omgservers/worker-api/v1/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.WORKER_SECURITY_SCHEMA)
public interface WorkerApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);

    @PUT
    @Path("/get-version")
    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request);

    @PUT
    @Path("/interchange")
    Uni<InterchangeWorkerResponse> interchange(InterchangeWorkerRequest request);
}
