package com.omgservers.module.runtime.impl.service.runtimeWebService.impl;

import com.omgservers.module.runtime.impl.service.runtimeShardedService.RuntimeShardedService;
import com.omgservers.module.runtime.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.dto.runtime.DeleteCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteCommandInternalResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeInternalResponse;
import com.omgservers.dto.runtime.SyncCommandShardedRequest;
import com.omgservers.dto.runtime.SyncCommandInternalResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeWebServiceImpl implements RuntimeWebService {

    final RuntimeShardedService runtimeShardedService;

    @Override
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardedRequest request) {
        return runtimeShardedService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardedRequest request) {
        return runtimeShardedService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardedRequest request) {
        return runtimeShardedService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardedRequest request) {
        return runtimeShardedService.syncCommand(request);
    }

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardedRequest request) {
        return runtimeShardedService.deleteCommand(request);
    }

    @Override
    public Uni<Void> doUpdate(DoUpdateShardedRequest request) {
        return runtimeShardedService.doUpdate(request);
    }
}
