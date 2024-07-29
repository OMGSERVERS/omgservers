package com.omgservers.service.entrypoint.support.impl.service.supportService;

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
import jakarta.validation.Valid;

public interface SupportService {

    Uni<CreateTokenSupportResponse> createToken(@Valid CreateTokenSupportRequest request);

    Uni<CreateTenantSupportResponse> createTenant(@Valid CreateTenantSupportRequest request);

    Uni<DeleteTenantSupportResponse> deleteTenant(@Valid DeleteTenantSupportRequest request);

    Uni<CreateProjectSupportResponse> createProject(@Valid CreateProjectSupportRequest request);

    Uni<DeleteProjectSupportResponse> deleteProject(@Valid DeleteProjectSupportRequest request);

    Uni<CreateDeveloperSupportResponse> createDeveloper(@Valid CreateDeveloperSupportRequest request);

    Uni<DeleteDeveloperSupportResponse> deleteDeveloper(@Valid DeleteDeveloperSupportRequest request);

    Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            @Valid CreateTenantPermissionsSupportRequest request);

    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            @Valid DeleteTenantPermissionsSupportRequest request);

    Uni<CreateProjectPermissionsSupportResponse> createProjectPermissions(
            @Valid CreateProjectPermissionsSupportRequest request);

    Uni<DeleteProjectPermissionsSupportResponse> deleteProjectPermissions(
            @Valid DeleteProjectPermissionsSupportRequest request);

    Uni<CreateStagePermissionsSupportResponse> createStagePermissions(
            @Valid CreateStagePermissionsSupportRequest request);

    Uni<DeleteStagePermissionsSupportResponse> deleteStagePermissions(
            @Valid DeleteStagePermissionsSupportRequest request);
}
