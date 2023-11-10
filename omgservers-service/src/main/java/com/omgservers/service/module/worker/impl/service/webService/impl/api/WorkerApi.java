package com.omgservers.service.module.worker.impl.service.webService.impl.api;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/worker-api/v1/request")
public interface WorkerApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);

    @PUT
    @Path("/get-version")
    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request);

    @PUT
    @Path("/get-worker-context")
    Uni<GetWorkerContextWorkerResponse> getWorkerContext(GetWorkerContextWorkerRequest request);

    @PUT
    @Path("/do-worker-commands")
    Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(DoWorkerCommandsWorkerRequest request);
}
