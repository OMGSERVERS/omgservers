package com.omgservers.service.entrypoint.support.impl.service.webService.impl.supportApi;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.CreateTokenSupportRequest;
import com.omgservers.model.dto.support.CreateTokenSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
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
    @Path("/create-developer")
    Uni<CreateDeveloperSupportResponse> createDeveloper(@NotNull CreateDeveloperSupportRequest request);

    @PUT
    @Path("/create-tenant-permissions")
    Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            @NotNull CreateTenantPermissionsSupportRequest request);

    @PUT
    @Path("/delete-tenant-permissions")
    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            @NotNull DeleteTenantPermissionsSupportRequest request);
}
