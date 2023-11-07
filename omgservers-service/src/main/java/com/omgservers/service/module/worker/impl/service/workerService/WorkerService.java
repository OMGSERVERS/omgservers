package com.omgservers.service.module.worker.impl.service.workerService;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface WorkerService {

    Uni<CreateTokenWorkerResponse> createToken(@Valid CreateTokenWorkerRequest request);

    Uni<GetVersionWorkerResponse> getVersion(@Valid GetVersionWorkerRequest request);

    Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(@Valid ViewRuntimeCommandsWorkerRequest request);

    Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(@Valid HandleRuntimeCommandsWorkerRequest request);

    Uni<GetRuntimeStateWorkerResponse> getRuntimeState(@Valid GetRuntimeStateWorkerRequest request);

    Uni<UpdateRuntimeStateWorkerResponse> updateRuntimeState(@Valid UpdateRuntimeStateWorkerRequest request);
}
