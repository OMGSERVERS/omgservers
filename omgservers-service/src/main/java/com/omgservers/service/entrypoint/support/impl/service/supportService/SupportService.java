package com.omgservers.service.entrypoint.support.impl.service.supportService;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface SupportService {

    Uni<CreateTokenSupportResponse> createToken(@Valid CreateTokenSupportRequest request);

    Uni<CreateTenantSupportResponse> createTenant(@Valid CreateTenantSupportRequest request);

    Uni<DeleteTenantSupportResponse> deleteTenant(@Valid DeleteTenantSupportRequest request);

    Uni<CreateTenantProjectSupportResponse> createTenantProject(@Valid CreateTenantProjectSupportRequest request);

    Uni<DeleteTenantProjectSupportResponse> deleteTenantProject(@Valid DeleteTenantProjectSupportRequest request);

    Uni<CreateDeveloperSupportResponse> createDeveloper(@Valid CreateDeveloperSupportRequest request);

    Uni<DeleteDeveloperSupportResponse> deleteDeveloper(@Valid DeleteDeveloperSupportRequest request);

    Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            @Valid CreateTenantPermissionsSupportRequest request);

    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            @Valid DeleteTenantPermissionsSupportRequest request);

    Uni<CreateTenantProjectPermissionsSupportResponse> createTenantProjectPermissions(
            @Valid CreateTenantProjectPermissionsSupportRequest request);

    Uni<DeleteProjectPermissionsSupportResponse> deleteTenantProjectPermissions(
            @Valid DeleteProjectPermissionsSupportRequest request);

    Uni<CreateTenantStagePermissionsSupportResponse> createTenantStagePermissions(
            @Valid CreateTenantStagePermissionsSupportRequest request);

    Uni<DeleteTenantStagePermissionsSupportResponse> deleteTenantStagePermissions(
            @Valid DeleteTenantStagePermissionsSupportRequest request);
}
