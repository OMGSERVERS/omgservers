package com.omgservers.service.entrypoint.support.impl.service.webService.impl.supportApi;

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
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Support Entrypoint API")
@Path("/omgservers/v1/entrypoint/support/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.SUPPORT_SECURITY_SCHEMA)
public interface SupportApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenSupportResponse> createToken(@NotNull CreateTokenSupportRequest request);

    @PUT
    @Path("/create-tenant")
    Uni<CreateTenantSupportResponse> createTenant(@NotNull CreateTenantSupportRequest request);

    @PUT
    @Path("/delete-tenant")
    Uni<DeleteTenantSupportResponse> deleteTenant(@NotNull DeleteTenantSupportRequest request);

    @PUT
    @Path("/create-project")
    Uni<CreateTenantProjectSupportResponse> createTenantProject(@NotNull CreateTenantProjectSupportRequest request);

    @PUT
    @Path("/delete-project")
    Uni<DeleteTenantProjectSupportResponse> deleteTenantProject(@NotNull DeleteTenantProjectSupportRequest request);

    @PUT
    @Path("/create-developer")
    Uni<CreateDeveloperSupportResponse> createDeveloper(@NotNull CreateDeveloperSupportRequest request);

    @PUT
    @Path("/delete-developer")
    Uni<DeleteDeveloperSupportResponse> deleteDeveloper(@NotNull DeleteDeveloperSupportRequest request);

    @PUT
    @Path("/create-tenant-permissions")
    Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            @NotNull CreateTenantPermissionsSupportRequest request);

    @PUT
    @Path("/delete-tenant-permissions")
    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            @NotNull DeleteTenantPermissionsSupportRequest request);

    @PUT
    @Path("/create-project-permissions")
    Uni<CreateTenantProjectPermissionsSupportResponse> createTenantProjectPermissions(
            @NotNull CreateTenantProjectPermissionsSupportRequest request);

    @PUT
    @Path("/delete-project-permissions")
    Uni<DeleteProjectPermissionsSupportResponse> deleteTenantProjectPermissions(
            @NotNull DeleteProjectPermissionsSupportRequest request);

    @PUT
    @Path("/create-stage-permissions")
    Uni<CreateTenantStagePermissionsSupportResponse> createTenantStagePermissions(
            @NotNull CreateTenantStagePermissionsSupportRequest request);

    @PUT
    @Path("/delete-stage-permissions")
    Uni<DeleteTenantStagePermissionsSupportResponse> deleteTenantStagePermissions(
            @NotNull DeleteTenantStagePermissionsSupportRequest request);
}
