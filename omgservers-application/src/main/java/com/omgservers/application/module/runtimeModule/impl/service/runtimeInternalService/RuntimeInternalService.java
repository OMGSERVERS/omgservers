package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService;

import com.omgservers.dto.runtimeModule.DeleteCommandInternalRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandInternalRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeInternalService {

    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request);

    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeInternalRequest request);

    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request);

    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request);

    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request);

    Uni<Void> doUpdate(DoUpdateInternalRequest request);
}
