package com.omgservers.service.entrypoint.worker.impl.service.webService.impl;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.InterchangeWorkerRequest;
import com.omgservers.model.dto.worker.InterchangeWorkerResponse;
import com.omgservers.service.entrypoint.worker.impl.service.workerService.WorkerService;
import com.omgservers.service.entrypoint.worker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final WorkerService workerService;

    @Override
    public Uni<CreateTokenWorkerResponse> createToken(final CreateTokenWorkerRequest request) {
        return workerService.createToken(request);
    }

    @Override
    public Uni<GetVersionWorkerResponse> getVersion(final GetVersionWorkerRequest request) {
        return workerService.getVersion(request);
    }

    @Override
    public Uni<InterchangeWorkerResponse> interchange(final InterchangeWorkerRequest request) {
        return workerService.interchange(request);
    }
}
