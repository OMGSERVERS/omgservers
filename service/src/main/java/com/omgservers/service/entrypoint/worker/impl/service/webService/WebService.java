package com.omgservers.service.entrypoint.worker.impl.service.webService;

import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerRequest;
import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerResponse;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerRequest;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerResponse;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerRequest;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);

    Uni<GetConfigWorkerResponse> getConfig(GetConfigWorkerRequest request);

    Uni<InterchangeWorkerResponse> interchange(InterchangeWorkerRequest request);
}
