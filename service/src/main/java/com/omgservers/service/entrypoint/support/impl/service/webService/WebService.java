package com.omgservers.service.entrypoint.support.impl.service.webService;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateProjectPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateProjectPermissionsSupportResponse;
import com.omgservers.model.dto.support.CreateProjectSupportRequest;
import com.omgservers.model.dto.support.CreateProjectSupportResponse;
import com.omgservers.model.dto.support.CreateStagePermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateStagePermissionsSupportResponse;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.CreateTokenSupportRequest;
import com.omgservers.model.dto.support.CreateTokenSupportResponse;
import com.omgservers.model.dto.support.DeleteDeveloperSupportRequest;
import com.omgservers.model.dto.support.DeleteDeveloperSupportResponse;
import com.omgservers.model.dto.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.model.dto.support.DeleteProjectSupportRequest;
import com.omgservers.model.dto.support.DeleteProjectSupportResponse;
import com.omgservers.model.dto.support.DeleteStagePermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteStagePermissionsSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
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
