package com.omgservers.worker.module.service.service;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/omgservers/worker-api/v1/request")
@RegisterRestClient(configKey = "omgservers")
public interface WorkerService {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);

    @PUT
    @Path("/get-version")
    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request,
                                             @HeaderParam("Authorization") String token);

    @PUT
    @Path("/get-worker-context")
    Uni<GetWorkerContextWorkerResponse> getWorkerContext(GetWorkerContextWorkerRequest request,
                                                         @HeaderParam("Authorization") String token);

    @PUT
    @Path("/do-worker-commands")
    Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(DoWorkerCommandsWorkerRequest request,
                                                         @HeaderParam("Authorization") String token);
}
