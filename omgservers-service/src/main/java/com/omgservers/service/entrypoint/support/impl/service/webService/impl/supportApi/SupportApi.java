package com.omgservers.service.entrypoint.support.impl.service.webService.impl.supportApi;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportResponse;
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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Support Entrypoint API")
@Path("/service/v1/entrypoint/support/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.SUPPORT_SECURITY_SCHEMA)
public interface SupportApi {

    @POST
    @Path("/create-token")
    Uni<CreateTokenSupportResponse> execute(@NotNull CreateTokenSupportRequest request);

    /*
    Tenant
     */

    @POST
    @Path("/create-tenant")
    Uni<CreateTenantSupportResponse> execute(@NotNull CreateTenantSupportRequest request);

    @POST
    @Path("/create-tenant-alias")
    Uni<CreateTenantAliasSupportResponse> execute(@NotNull CreateTenantAliasSupportRequest request);

    @POST
    @Path("/delete-tenant")
    Uni<DeleteTenantSupportResponse> execute(@NotNull DeleteTenantSupportRequest request);

    /*
    Project
     */

    @POST
    @Path("/create-project")
    Uni<CreateTenantProjectSupportResponse> execute(@NotNull CreateTenantProjectSupportRequest request);

    @POST
    @Path("/create-project-alias")
    Uni<CreateTenantProjectAliasSupportResponse> execute(@NotNull CreateTenantProjectAliasSupportRequest request);

    @POST
    @Path("/delete-project")
    Uni<DeleteTenantProjectSupportResponse> execute(@NotNull DeleteTenantProjectSupportRequest request);

    /*
    Developer
     */

    @POST
    @Path("/create-developer")
    Uni<CreateDeveloperSupportResponse> execute(@NotNull CreateDeveloperSupportRequest request);

    @POST
    @Path("/delete-developer")
    Uni<DeleteDeveloperSupportResponse> execute(@NotNull DeleteDeveloperSupportRequest request);

    /*
    Permissions
     */

    @POST
    @Path("/create-tenant-permissions")
    Uni<CreateTenantPermissionsSupportResponse> execute(@NotNull CreateTenantPermissionsSupportRequest request);

    @POST
    @Path("/delete-tenant-permissions")
    Uni<DeleteTenantPermissionsSupportResponse> execute(@NotNull DeleteTenantPermissionsSupportRequest request);

    @POST
    @Path("/create-project-permissions")
    Uni<CreateTenantProjectPermissionsSupportResponse> execute(
            @NotNull CreateTenantProjectPermissionsSupportRequest request);

    @POST
    @Path("/delete-project-permissions")
    Uni<DeleteProjectPermissionsSupportResponse> execute(@NotNull DeleteProjectPermissionsSupportRequest request);

    @POST
    @Path("/create-stage-permissions")
    Uni<CreateTenantStagePermissionsSupportResponse> execute(
            @NotNull CreateTenantStagePermissionsSupportRequest request);

    @POST
    @Path("/delete-stage-permissions")
    Uni<DeleteTenantStagePermissionsSupportResponse> execute(
            @NotNull DeleteTenantStagePermissionsSupportRequest request);
}
