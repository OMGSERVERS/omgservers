package com.omgservers.worker.module.service.service;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
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
    @Path("/view-runtime-commands")
    Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(ViewRuntimeCommandsWorkerRequest request,
                                                               @HeaderParam("Authorization") String token);

    @PUT
    @Path("/handle-runtime-commands")
    Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(HandleRuntimeCommandsWorkerRequest request,
                                                                   @HeaderParam("Authorization") String token);
}
