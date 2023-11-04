package com.omgservers.service.module.worker.impl.service.webService.impl.api;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
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
    @Path("/view-runtime-commands")
    Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(ViewRuntimeCommandsWorkerRequest request);

    @PUT
    @Path("/handle-runtime-commands")
    Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(HandleRuntimeCommandsWorkerRequest request);
}
