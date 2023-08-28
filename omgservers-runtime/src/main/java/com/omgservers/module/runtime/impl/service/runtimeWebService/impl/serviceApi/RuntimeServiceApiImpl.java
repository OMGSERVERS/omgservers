package com.omgservers.module.runtime.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeShardedResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.runtime.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeServiceApiImpl implements RuntimeServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final RuntimeWebService runtimeWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeShardedResponse> syncRuntime(SyncRuntimeShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::syncRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetRuntimeShardedResponse> getRuntime(GetRuntimeShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::getRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::deleteRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::syncRuntimeCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::deleteRuntimeCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(DoRuntimeUpdateShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::doRuntimeUpdate);
    }
}
