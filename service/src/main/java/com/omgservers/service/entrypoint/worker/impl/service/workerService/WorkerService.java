package com.omgservers.service.entrypoint.worker.impl.service.workerService;

import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerRequest;
import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerResponse;
import com.omgservers.schema.entrypoint.worker.GetVersionWorkerRequest;
import com.omgservers.schema.entrypoint.worker.GetVersionWorkerResponse;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerRequest;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface WorkerService {

    Uni<CreateTokenWorkerResponse> createToken(@Valid CreateTokenWorkerRequest request);

    Uni<GetVersionWorkerResponse> getVersion(@Valid GetVersionWorkerRequest request);

    Uni<InterchangeWorkerResponse> interchange(@Valid InterchangeWorkerRequest request);
}
