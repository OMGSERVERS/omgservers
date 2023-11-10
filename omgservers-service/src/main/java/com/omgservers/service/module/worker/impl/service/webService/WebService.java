package com.omgservers.service.module.worker.impl.service.webService;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);

    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request);

    Uni<GetWorkerContextWorkerResponse> getWorkerContext(GetWorkerContextWorkerRequest request);

    Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(DoWorkerCommandsWorkerRequest request);
}
