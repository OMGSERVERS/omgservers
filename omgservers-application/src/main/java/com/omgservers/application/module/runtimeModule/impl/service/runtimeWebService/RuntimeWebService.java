package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService;

import com.omgservers.dto.runtimeModule.DeleteCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateRoutedRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeWebService {
    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeRoutedRequest request);

    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeRoutedRequest request);

    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeRoutedRequest request);

    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandRoutedRequest request);

    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandRoutedRequest request);

    Uni<Void> doUpdate(DoUpdateRoutedRequest request);
}
