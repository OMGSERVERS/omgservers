package com.omgservers.module.tenant.impl.service.projectShardedService;

import com.omgservers.dto.tenantModule.DeleteProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ProjectShardedService {

    Uni<GetProjectInternalResponse> getProject(GetProjectShardRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardRequest request);

    Uni<Void> deleteProject(DeleteProjectShardRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request);
}
