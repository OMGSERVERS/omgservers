package com.omgservers.service.entrypoint.support.impl.service.webService;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenSupportResponse> createToken(CreateTokenSupportRequest request);

    Uni<CreateTenantSupportResponse> createTenant(CreateTenantSupportRequest request);

    Uni<DeleteTenantSupportResponse> deleteTenant(DeleteTenantSupportRequest request);

    Uni<CreateProjectSupportResponse> createProject(CreateProjectSupportRequest request);

    Uni<DeleteProjectSupportResponse> deleteProject(DeleteProjectSupportRequest request);

    Uni<CreateDeveloperSupportResponse> createDeveloper(CreateDeveloperSupportRequest request);

    Uni<DeleteDeveloperSupportResponse> deleteDeveloper(DeleteDeveloperSupportRequest request);

    Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(CreateTenantPermissionsSupportRequest request);

    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(DeleteTenantPermissionsSupportRequest request);

    Uni<CreateProjectPermissionsSupportResponse> createProjectPermissions(
            CreateProjectPermissionsSupportRequest request);

    Uni<DeleteProjectPermissionsSupportResponse> deleteProjectPermissions(
            DeleteProjectPermissionsSupportRequest request);

    Uni<CreateStagePermissionsSupportResponse> createStagePermissions(CreateStagePermissionsSupportRequest request);

    Uni<DeleteStagePermissionsSupportResponse> deleteStagePermissions(DeleteStagePermissionsSupportRequest request);
}
