package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService;

import com.omgservers.dto.runtimeModule.DeleteCommandShardRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandShardRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeInternalService {

    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardRequest request);

    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardRequest request);

    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardRequest request);

    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardRequest request);

    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardRequest request);

    Uni<Void> doUpdate(DoUpdateShardRequest request);
}
