package com.omgservers.service.module.worker.impl.service.workerService;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface WorkerService {

    Uni<CreateTokenWorkerResponse> createToken(@Valid CreateTokenWorkerRequest request);

    Uni<GetVersionWorkerResponse> getVersion(@Valid GetVersionWorkerRequest request);

    Uni<GetWorkerContextWorkerResponse> getWorkerContext(@Valid GetWorkerContextWorkerRequest request);

    Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(@Valid DoWorkerCommandsWorkerRequest request);
}
