package com.omgservers.application.module.tenantModule.impl.service.projectInternalService;

import com.omgservers.dto.tenantModule.DeleteProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ProjectInternalService {

    Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request);

    Uni<Void> deleteProject(DeleteProjectInternalRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request);
}
