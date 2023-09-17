package com.omgservers.module.runtime.impl.service.webService.impl;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.MarkRuntimeCommandsRequest;
import com.omgservers.dto.runtime.MarkRuntimeCommandsResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.module.runtime.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RuntimeService runtimeService;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(final SyncRuntimeRequest request) {
        return runtimeService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(final GetRuntimeRequest request) {
        return runtimeService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(final DeleteRuntimeRequest request) {
        return runtimeService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(final SyncRuntimeCommandRequest request) {
        return runtimeService.syncRuntimeCommand(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(final DeleteRuntimeCommandRequest request) {
        return runtimeService.deleteRuntimeCommand(request);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(final ViewRuntimeCommandsRequest request) {
        return runtimeService.viewRuntimeCommands(request);
    }

    @Override
    public Uni<MarkRuntimeCommandsResponse> markRuntimeCommands(final MarkRuntimeCommandsRequest request) {
        return runtimeService.markRuntimeCommands(request);
    }

    @Override
    public Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(final SyncRuntimeGrantRequest request) {
        return runtimeService.syncRuntimeGrant(request);
    }

    @Override
    public Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(final DeleteRuntimeGrantRequest request) {
        return runtimeService.deleteRuntimeGrant(request);
    }
}
