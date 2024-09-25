package com.omgservers.service.entrypoint.support.impl.service.webService;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
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
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenSupportResponse> createToken(CreateTokenSupportRequest request);

    Uni<CreateTenantSupportResponse> createTenant(CreateTenantSupportRequest request);

    Uni<DeleteTenantSupportResponse> deleteTenant(DeleteTenantSupportRequest request);

    Uni<CreateTenantProjectSupportResponse> createTenantProject(CreateTenantProjectSupportRequest request);

    Uni<DeleteTenantProjectSupportResponse> deleteTenantProject(DeleteTenantProjectSupportRequest request);

    Uni<CreateDeveloperSupportResponse> createDeveloper(CreateDeveloperSupportRequest request);

    Uni<DeleteDeveloperSupportResponse> deleteDeveloper(DeleteDeveloperSupportRequest request);

    Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(CreateTenantPermissionsSupportRequest request);

    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(DeleteTenantPermissionsSupportRequest request);

    Uni<CreateTenantProjectPermissionsSupportResponse> createTenantProjectPermissions(
            CreateTenantProjectPermissionsSupportRequest request);

    Uni<DeleteProjectPermissionsSupportResponse> deleteTenantProjectPermissions(
            DeleteProjectPermissionsSupportRequest request);

    Uni<CreateTenantStagePermissionsSupportResponse> createTenantStagePermissions(CreateTenantStagePermissionsSupportRequest request);

    Uni<DeleteTenantStagePermissionsSupportResponse> deleteTenantStagePermissions(DeleteTenantStagePermissionsSupportRequest request);
}
