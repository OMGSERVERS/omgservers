package com.omgservers.service.entrypoint.worker.impl.service.webService;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.InterchangeWorkerRequest;
import com.omgservers.model.dto.worker.InterchangeWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);

    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request);

    Uni<InterchangeWorkerResponse> interchange(InterchangeWorkerRequest request);
}
