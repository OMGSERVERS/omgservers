package com.omgservers.module.runtime.impl.service.runtimeWebService.impl;

import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.RuntimeShardedService;
import com.omgservers.module.runtime.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
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
    public Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request) {
        return runtimeShardedService.syncRuntimeCommand(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request) {
        return runtimeShardedService.deleteRuntimeCommand(request);
    }

    @Override
    public Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(DoRuntimeUpdateShardedRequest request) {
        return runtimeShardedService.doRuntimeUpdate(request);
    }
}
