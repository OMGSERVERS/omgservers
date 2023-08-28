package com.omgservers.module.runtime.impl.service.runtimeWebService.impl;

import com.omgservers.module.runtime.impl.service.runtimeShardedService.RuntimeShardedService;
import com.omgservers.module.runtime.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeShardedResponse;
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
    public Uni<SyncRuntimeShardedResponse> syncRuntime(SyncRuntimeShardedRequest request) {
        return runtimeShardedService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeShardedResponse> getRuntime(GetRuntimeShardedRequest request) {
        return runtimeShardedService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request) {
        return runtimeShardedService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncRuntimeCommandShardedResponse> syncCommand(SyncRuntimeCommandShardedRequest request) {
        return runtimeShardedService.syncCommand(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandShardedResponse> deleteCommand(DeleteRuntimeCommandShardedRequest request) {
        return runtimeShardedService.deleteCommand(request);
    }

    @Override
    public Uni<Void> doUpdate(DoUpdateShardedRequest request) {
        return runtimeShardedService.doUpdate(request);
    }
}
