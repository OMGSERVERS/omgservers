package com.omgservers.application.module.tenantModule.impl.service.projectInternalService;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.*;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.GetProjectInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.HasProjectPermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectPermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ProjectInternalService {

    Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request);

    Uni<Void> deleteProject(DeleteProjectInternalRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request);
}
