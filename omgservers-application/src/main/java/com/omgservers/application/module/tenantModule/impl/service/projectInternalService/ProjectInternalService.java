package com.omgservers.application.module.tenantModule.impl.service.projectInternalService;

import com.omgservers.dto.tenantModule.DeleteProjectRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ProjectInternalService {

    Uni<GetProjectInternalResponse> getProject(GetProjectRoutedRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectRoutedRequest request);

    Uni<Void> deleteProject(DeleteProjectRoutedRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionRoutedRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionRoutedRequest request);
}
