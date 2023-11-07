package com.omgservers.service.module.worker.impl.service.webService;

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

public interface WebService {

    Uni<CreateTokenWorkerResponse> createToken(CreateTokenWorkerRequest request);

    Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request);

    Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(ViewRuntimeCommandsWorkerRequest request);

    Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(HandleRuntimeCommandsWorkerRequest request);

    Uni<GetRuntimeStateWorkerResponse> getRuntimeState(GetRuntimeStateWorkerRequest request);

    Uni<UpdateRuntimeStateWorkerResponse> updateRuntimeState(UpdateRuntimeStateWorkerRequest request);
}
